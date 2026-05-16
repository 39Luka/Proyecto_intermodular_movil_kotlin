package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.data.Category
import net.iesochoa.silvia.projecto_intermodular.data.Product

/**
 * Estado de la interfaz para la gestión del catálogo de productos.
 */
data class ProductUiState(
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val detailLoading: Boolean = false,
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val error: String? = null
)
