package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import net.iesochoa.silvia.projecto_intermodular.data.Category

data class CatalogUiState(
    val searchQuery: String = "",
    val products: List<CardItem> = emptyList(),
    val filteredProducts: List<CardItem> = emptyList(),
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 1,
    val pageSize: Int = 12,
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Int? = null,
    val showFilterDialog: Boolean = false
)
