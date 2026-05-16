package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import net.iesochoa.silvia.projecto_intermodular.model.PurchaseDetailUiState
import javax.inject.Inject

/**
 * ViewModel que gestiona el detalle de una compra específica.
 */
@HiltViewModel
class PurchaseDetailViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchaseDetailUiState())
    val uiState: StateFlow<PurchaseDetailUiState> = _uiState.asStateFlow()

    /** Carga los datos de una compra por su ID. */
    fun loadPurchase(purchaseId: Int) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val purchase = purchaseRepository.getPurchaseById(purchaseId)
                _uiState.update { it.copy(
                    purchase = purchase,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar compra"
                ) }
            }
        }
    }

    /** Intenta marcar la compra actual como pagada. */
    fun payPurchase() {
        val purchase = _uiState.value.purchase ?: return
        _uiState.update { it.copy(isProcessing = true) }
        viewModelScope.launch {
            try {
                purchaseRepository.payPurchase(purchase.id)
                loadPurchase(purchase.id)
            } catch (e: Exception) {
                _uiState.update { it.copy(isProcessing = false, error = "Error al pagar: ${e.message}") }
            }
        }
    }

    /** Intenta cancelar la compra actual. */
    fun cancelPurchase() {
        val purchase = _uiState.value.purchase ?: return
        _uiState.update { it.copy(isProcessing = true) }
        viewModelScope.launch {
            try {
                purchaseRepository.cancelPurchase(purchase.id)
                loadPurchase(purchase.id)
            } catch (e: Exception) {
                _uiState.update { it.copy(isProcessing = false, error = "Error al cancelar: ${e.message}") }
            }
        }
    }
}
