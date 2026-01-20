// model/OffersUiState.kt
package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem

data class OffersUiState(
    val searchQuery: String = "",
    val allProducts: List<HorizontalCardItem> = emptyList(),
    val filteredProducts: List<HorizontalCardItem> = emptyList()
)
