package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.data.Purchase
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import net.iesochoa.silvia.projecto_intermodular.model.PedidoItem
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.utils.ErrorMapper
import net.iesochoa.silvia.projecto_intermodular.ui.utils.toCurrency
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
        
        // Formatear fechas para la API (ISO 8601: yyyy-MM-ddTHH:mm:ss)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val startStr = startDate?.let { 
            try {
                val instant = java.time.Instant.ofEpochMilli(it)
                val localDateTime = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                localDateTime.format(formatter)
            } catch (e: Exception) {
                null
            }
        }
        val endStr = endDate?.let { 
            try {
                val instant = java.time.Instant.ofEpochMilli(it)
                // Para la fecha de fin, agregar 23:59:59 para incluir todo el día
                val localDateTime = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().plusHours(23).plusMinutes(59).plusSeconds(59)
                localDateTime.format(formatter)
            } catch (e: Exception) {
                null
            }
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val user = authRepository.getUser().first()
                if (user != null) {
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
                            total = purchase.total.toCurrency(),
                            discount = if ((purchase.discount ?: 0.0) > 0.0) {
                                "-${purchase.discount.toCurrency()}"
                            } else null
                        )
                    }
                    
                    _uiState.update { it.copy(
                        pedidos = uiItems,
                        totalPages = response.totalPages,
                        isLoading = false
                    ) }
                } else {
                    _uiState.update { it.copy(
                        isLoading = false, 
                        error = "Debes iniciar sesión para ver tus pedidos."
                    ) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false, 
                    error = ErrorMapper.map(e, "No se pudo cargar el historial de pedidos.")
                ) }
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
       _uiState.update { it.copy(startDate = start, endDate = end, currentPage = 0, error = null) }
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
