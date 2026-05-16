package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.Purchase
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.model.PurchaseUiState
import javax.inject.Inject

/**
 * ViewModel para la gestión de compras generales.
 */
@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchaseUiState())
    val uiState: StateFlow<PurchaseUiState> = _uiState.asStateFlow()

    /** Carga la lista de compras del usuario. */
    fun loadPurchases(userId: Int? = null) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val response = purchaseRepository.getPurchases(userId)
                _uiState.value = _uiState.value.copy(
                    purchases = response.content,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar compras"
                )
            }
        }
    }

    /** Carga el detalle de una compra específica. */
    fun loadPurchaseDetail(purchaseId: Int) {
        _uiState.value = _uiState.value.copy(detailLoading = true)
        viewModelScope.launch {
            try {
                val purchase = purchaseRepository.getPurchaseById(purchaseId)
                _uiState.value = _uiState.value.copy(
                    selectedPurchase = purchase,
                    detailLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    detailLoading = false,
                    error = e.message ?: "Error al cargar detalle"
                )
            }
        }
    }

    fun payPurchase(purchaseId: Int) {
        _uiState.value = _uiState.value.copy(isProcessing = true)
        viewModelScope.launch {
            try {
                purchaseRepository.payPurchase(purchaseId)
                loadPurchaseDetail(purchaseId)
                _uiState.value = _uiState.value.copy(isProcessing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isProcessing = false,
                    error = e.message ?: "Error al pagar"
                )
            }
        }
    }

    fun cancelPurchase(purchaseId: Int) {
        _uiState.value = _uiState.value.copy(isProcessing = true)
        viewModelScope.launch {
            try {
                purchaseRepository.cancelPurchase(purchaseId)
                loadPurchases()
                _uiState.value = _uiState.value.copy(isProcessing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isProcessing = false,
                    error = e.message ?: "Error al cancelar"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
