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
import net.iesochoa.silvia.projecto_intermodular.data.Purchase
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import net.iesochoa.silvia.projecto_intermodular.model.PedidoItem
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.utils.toCurrency
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel encargado de la gestión y visualización del historial de compras del usuario.
 */
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

    /** Observa cambios en el perfil del usuario para actualizar la imagen de perfil. */
    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    /** Carga la lista de compras del usuario desde el repositorio aplicando filtros de fecha si existen. */
    private fun loadPurchases() {
        val pageSize = _uiState.value.pageSize
        val currentPage = _uiState.value.currentPage
        val startDate = _uiState.value.startDate
        val endDate = _uiState.value.endDate
        
        // Formatear fechas para la API (yyyy-MM-dd)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startStr = startDate?.let { sdf.format(java.util.Date(it)) }
        val endStr = endDate?.let { sdf.format(java.util.Date(it)) }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                authRepository.getUser().collect { user ->
                    if (user != null) {
                        try {
                            val response = purchaseRepository.getPurchases(
                                userId = user.id, 
                                page = currentPage, 
                                pageSize = pageSize,
                                startDate = startStr,
                                endDate = endStr
                            )
                            
                            val uiItems = response.content.map { purchase ->
                                PedidoItem(
                                    id = purchase.id,
                                    fecha = purchase.createdAt?.take(10) ?: "N/A",
                                    estado = purchase.status ?: "PENDIENTE",
                                    total = purchase.total.toCurrency()
                                )
                            }
                            
                            _uiState.update { it.copy(
                                pedidos = uiItems,
                                totalPages = response.totalPages,
                                isLoading = false
                            ) }
                        } catch (e: Exception) {
                            _uiState.update { it.copy(
                                isLoading = false,
                                error = "No se pudo cargar el historial de pedidos."
                            ) }
                        }
                    } else {
                        _uiState.update { it.copy(
                            isLoading = false, 
                            error = "Debes iniciar sesión para ver tus pedidos."
                        ) }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error inesperado.") }
            }
        }
    }

    // --- Acciones de Usuario ---

    /**
     * Actualiza el rango de fechas para el filtrado y recarga desde el servidor.
     * @param start Marca de tiempo inicial en milisegundos.
     * @param end Marca de tiempo final en milisegundos.
     */
    fun onDateRangeSelected(start: Long?, end: Long?) {
        _uiState.update { it.copy(startDate = start, endDate = end, currentPage = 0) }
        loadPurchases()
    }

    /** Restablece todos los filtros y recarga la lista. */
    fun clearFilter() {
        _uiState.update { it.copy(startDate = null, endDate = null, searchQuery = "", currentPage = 0) }
        loadPurchases()
    }

    /** Navega a la siguiente página de resultados. */
    fun goToNextPage() {
        val current = _uiState.value.currentPage
        val total = _uiState.value.totalPages
        if (current < total - 1) {
            _uiState.update { it.copy(currentPage = current + 1) }
            loadPurchases()
        }
    }

    /** Navega a la página anterior de resultados. */
    fun goToPreviousPage() {
        val current = _uiState.value.currentPage
        if (current > 0) {
            _uiState.update { it.copy(currentPage = current - 1) }
            loadPurchases()
        }
    }
}
