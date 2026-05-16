package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.data.Purchase

/** Estado del detalle de una compra específica. */
data class PurchaseDetailUiState(
    val purchase: Purchase? = null,
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null
)
