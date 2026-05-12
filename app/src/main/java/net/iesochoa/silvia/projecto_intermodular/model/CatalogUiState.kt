package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem

data class CatalogUiState(
    val searchQuery: String = "",
    val products: List<CardItem> = emptyList(),
    val filteredProducts: List<CardItem> = emptyList(),
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
