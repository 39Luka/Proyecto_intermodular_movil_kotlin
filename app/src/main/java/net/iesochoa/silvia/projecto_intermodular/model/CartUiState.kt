// model/CartUiState.kt
package net.iesochoa.silvia.projecto_intermodular.model

import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.Oferta

data class CartUiState(
    val products: List<HorizontalCardItem> = emptyList(),
    val ofertasDisponibles: List<Oferta> = emptyList(),
    val ofertasSeleccionadas: Set<Oferta> = emptySet()
)
