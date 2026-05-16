package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.data.CartRepository
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.model.ProductDetailUiState
import javax.inject.Inject

/**
 * ViewModel que gestiona la lógica de la pantalla de detalle de un producto.
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        observeUser()
    }

    /** Observa el usuario para actualizar la imagen de perfil. */
    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    /** Carga los datos del producto por su ID. */
    fun loadProduct(productId: Int) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(productId)
                _uiState.update { it.copy(
                    product = product,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar producto"
                ) }
            }
        }
    }

    /** Actualiza la cantidad seleccionada para añadir al carrito. */
    fun updateQuantity(quantity: Int) {
        _uiState.update { it.copy(quantity = quantity) }
    }

    /** Añade el producto actual al carrito con la cantidad seleccionada. */
    fun addToCart() {
        val product = _uiState.value.product ?: return
        val quantity = _uiState.value.quantity
        cartRepository.addToCart(product, quantity)
        _uiState.update { it.copy(
            quantity = 1,
            addedToCart = true
        ) }
    }
}
