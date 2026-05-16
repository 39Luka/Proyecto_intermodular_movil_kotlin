package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.data.Purchase

/**
 * Estado de la interfaz para la gestión general de compras (listado y detalle simple).
 */
data class PurchaseUiState(
    val purchases: List<Purchase> = emptyList(),
    val selectedPurchase: Purchase? = null,
    val isLoading: Boolean = false,
    val detailLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null
)
