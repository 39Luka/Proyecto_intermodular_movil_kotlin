package net.iesochoa.silvia.projecto_intermodular.model


/**
 * Estado de la interfaz de usuario para la pantalla de inicio (Home).
 * Contiene las listas de productos destacados, novedades y el estado de la búsqueda.
 */
data class HomeUiState(
    val promociones: List<CardItem> = emptyList(),
    val topVentas: List<CardItem> = emptyList(),
    val searchQuery: String = "",
    val filteredPromociones: List<CardItem> = emptyList(),
    val filteredTopVentas: List<CardItem> = emptyList(),
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
