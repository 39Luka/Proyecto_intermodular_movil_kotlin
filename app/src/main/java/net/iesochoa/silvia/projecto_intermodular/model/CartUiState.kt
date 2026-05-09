package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.data.Product
import net.iesochoa.silvia.projecto_intermodular.data.Promotion
import net.iesochoa.silvia.projecto_intermodular.ui.components.Oferta

data class CartUiState(
    val items: List<CartItemState> = emptyList(),
    val total: Double = 0.0,
    val isProcessing: Boolean = false,
    val error: String? = null
)

data class CartItemState(
    val product: Product,
    val quantity: Int,
    val applicablePromotions: List<Promotion> = emptyList(),
    val selectedPromotionId: Int? = null
)
