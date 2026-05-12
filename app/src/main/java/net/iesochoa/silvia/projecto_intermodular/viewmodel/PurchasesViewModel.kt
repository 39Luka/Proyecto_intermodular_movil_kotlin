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
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoItem
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PurchasesViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchasesUiState())
    val uiState: StateFlow<PurchasesUiState> = _uiState.asStateFlow()

    init {
        loadPurchases()
        observeUser()
    }

    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    fun loadPurchases() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                authRepository.getUser().collect { user ->
                    if (user != null) {
                        try {
                            val purchases = purchaseRepository.getPurchases(userId = user.id)
                            val items = purchases.map { purchase ->
                                PedidoItem(
                                    id = purchase.id,
                                    fecha = purchase.createdAt?.take(10) ?: "N/A",
                                    estado = purchase.status ?: "PENDIENTE",
                                    total = "€${String.format(Locale.US, "%.2f", purchase.total ?: 0.0)}"
                                )
                            }
                            _uiState.update { it.copy(pedidos = items, isLoading = false) }
                        } catch (e: Exception) {
                            _uiState.update { it.copy(
                                isLoading = false,
                                error = "No se pudo cargar el historial de pedidos."
                            ) }
                        }
                    } else {
                         _uiState.update { it.copy(isLoading = false, error = "Debes iniciar sesión para ver tus pedidos.") }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error inesperado.") }
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }
}
