package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.data.Product
import net.iesochoa.silvia.projecto_intermodular.data.Promotion

/** Estado del carrito de compras con items y total. */
data class CartUiState(
    val items: List<CartItemState> = emptyList(),
    val total: Double = 0.0,
    val userProfileImage: String? = null,
    val isProcessing: Boolean = false,
    val error: String? = null
)

/** Item del carrito con producto, cantidad y promociones disponibles. */
data class CartItemState(
    val product: Product,
    val quantity: Int,
    val applicablePromotions: List<Promotion> = emptyList(),
    val selectedPromotionId: Int? = null
)
