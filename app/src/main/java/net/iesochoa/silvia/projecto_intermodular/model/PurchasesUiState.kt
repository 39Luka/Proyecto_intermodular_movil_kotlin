package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoItem

data class PurchasesUiState(
    val searchQuery: String = "",
    val pedidos: List<PedidoItem> = emptyList()
)