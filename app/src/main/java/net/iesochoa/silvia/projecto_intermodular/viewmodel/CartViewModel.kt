package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.*
import net.iesochoa.silvia.projecto_intermodular.model.CartUiState
import net.iesochoa.silvia.projecto_intermodular.model.CartItemState
import net.iesochoa.silvia.projecto_intermodular.ui.utils.ErrorMapper
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

/**
 * ViewModel que gestiona el carrito de la compra.
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
    private val promotionRepository: PromotionRepository,
    private val authRepository: AuthRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        observeUser()
        observeCartItems()
    }

    /** Observa los items del carrito y busca promociones para cada uno. */
    private fun observeCartItems() {
        viewModelScope.launch {
            cartRepository.items.collect { repoItems ->
                val currentItems = _uiState.value.items
                
                val updatedItems = repoItems.map { repoItem ->
                    val existing = currentItems.find { it.product.id == repoItem.product.id }
                    if (existing != null && existing.product == repoItem.product && existing.quantity == repoItem.quantity) {
                        existing
                    } else {
                        CartItemState(repoItem.product, repoItem.quantity)
                    }
                }

                _uiState.update { it.copy(items = updatedItems) }
                
                updatedItems.forEach { item ->
                    fetchPromotionsForItem(item.product.id)
                }
                calculateTotal()
            }
        }
    }

    /** Obtiene la imagen de perfil del usuario actual. */
    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    /** Obtiene las promociones activas para un producto en el carrito. */
    private fun fetchPromotionsForItem(productId: Int) {
        viewModelScope.launch {
            try {
                val user = authRepository.getUser().first()
                val promos = promotionRepository.getActivePromotions(productId, user?.id)
                _uiState.update { state ->
                    val newItems = state.items.map { item ->
                        if (item.product.id == productId) {
                            item.copy(applicablePromotions = promos)
                        } else item
                    }
                    state.copy(items = newItems)
                }
                calculateTotal()
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error fetching promos for $productId", e)
            }
        }
    }

    /** Actualiza la cantidad de un producto en el carrito. */
    fun updateQuantity(productId: Int, newQuantity: Int) {
        cartRepository.updateQuantity(productId, newQuantity)
    }

    /** Elimina un producto del carrito. */
    fun removeItem(productId: Int) {
        cartRepository.removeItem(productId)
    }

    /** Selecciona una promoción específica para un producto del carrito. */
    fun onPromotionSelected(productId: Int, promotionId: Int?) {
        _uiState.update { state ->
            val newItems = state.items.map { item ->
                if (item.product.id == productId) {
                    item.copy(selectedPromotionId = promotionId)
                } else item
            }
            state.copy(items = newItems)
        }
        calculateTotal()
    }

    /** Calcula el precio total aplicando descuentos de las promociones seleccionadas. */
    private fun calculateTotal() {
        var total = 0.0
        _uiState.value.items.forEach { item ->
            val price = item.product.price ?: 0.0
            val subtotal = price * item.quantity
            
            val selectedPromo = item.applicablePromotions.find { it.id == item.selectedPromotionId }
            val discount = if (selectedPromo != null) {
                subtotal * ((selectedPromo.discountPercentage ?: 0.0) / 100.0)
            } else 0.0
            
            total += (subtotal - discount)
        }
        _uiState.update { it.copy(total = total.coerceAtLeast(0.0)) }
    }

    /** Procesa la compra de los productos actuales y vacía el carrito tras el éxito. */
    fun checkout(onSuccess: () -> Unit) {
        _uiState.update { it.copy(isProcessing = true, error = null) }
        viewModelScope.launch {
            try {
                val user = authRepository.getUser().first()
                if (user != null) {
                    val requests = _uiState.value.items.map { item ->
                        PurchaseItemRequest(
                            productId = item.product.id,
                            quantity = item.quantity,
                            promotionId = item.selectedPromotionId
                        )
                    }
                    purchaseRepository.createPurchase(requests, user.id)
                    cartRepository.clearCart()
                    _uiState.update { it.copy(isProcessing = false) }
                    onSuccess()
                } else {
                    _uiState.update { it.copy(isProcessing = false, error = "Debes iniciar sesión") }
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Checkout failed", e)
                _uiState.update { it.copy(
                    isProcessing = false, 
                    error = ErrorMapper.map(e, "Error al procesar la compra")
                ) }
            }
        }
    }
}
