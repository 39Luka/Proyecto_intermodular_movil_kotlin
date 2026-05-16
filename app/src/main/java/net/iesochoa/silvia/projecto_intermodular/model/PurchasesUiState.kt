package net.iesochoa.silvia.projecto_intermodular.model

/** Estado del historial de compras con filtrado por fechas y paginación. */
data class PurchasesUiState(
    val searchQuery: String = "",
    val startDate: Long? = null,
    val endDate: Long? = null,
    val pedidos: List<PedidoItem> = emptyList(),
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 1,
    val pageSize: Int = 10
)
