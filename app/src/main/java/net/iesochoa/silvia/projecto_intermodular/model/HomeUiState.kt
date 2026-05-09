package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem


data class HomeUiState(
    val promociones: List<CardItem> = emptyList(),
    val topVentas: List<CardItem> = emptyList(),
    val searchQuery: String = "",
    val filteredPromociones: List<CardItem> = emptyList(),
    val filteredTopVentas: List<CardItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
