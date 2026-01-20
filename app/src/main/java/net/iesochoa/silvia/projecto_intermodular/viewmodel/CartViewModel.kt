// viewmodel/CartViewModel.kt
package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.model.CartUiState
import net.iesochoa.silvia.projecto_intermodular.data.FakeRepository
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.Oferta

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        CartUiState(
            products = FakeRepository.horizontalProducts,
            ofertasDisponibles = FakeRepository.ofertas,
            ofertasSeleccionadas = emptySet()
        )
    )
    val uiState: StateFlow<CartUiState> = _uiState

    // 🔹 Seleccionar / deseleccionar oferta
    fun onOfertaSeleccionada(oferta: Oferta) {
        _uiState.update { state ->
            val nuevas = state.ofertasSeleccionadas.toMutableSet()
            if (nuevas.contains(oferta)) nuevas.remove(oferta)
            else nuevas.add(oferta)
            state.copy(ofertasSeleccionadas = nuevas)
        }
    }

    fun onProductoAgregado(producto: HorizontalCardItem) {
        _uiState.update { state ->
            state.copy(products = state.products + producto)
        }
    }

    fun onProductoEliminado(producto: HorizontalCardItem) {
        _uiState.update { state ->
            state.copy(products = state.products - producto)
        }
    }
}
