package net.iesochoa.silvia.projecto_intermodular.model

/** Estado de la interfaz para la gestión del catálogo de productos. */
data class OffersUiState(
    val searchQuery: String = "",
    val allProducts: List<HorizontalCardItem> = emptyList(),
    val filteredProducts: List<HorizontalCardItem> = emptyList(),
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 1,
    val pageSize: Int = 12
)
