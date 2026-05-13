// model/OffersUiState.kt
package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem

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
