package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.data.Product

/** Estado del detalle de un producto con cantidad y opciones de carrito. */
data class ProductDetailUiState(
    val product: Product? = null,
    val quantity: Int = 1,
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val addedToCart: Boolean = false
)
