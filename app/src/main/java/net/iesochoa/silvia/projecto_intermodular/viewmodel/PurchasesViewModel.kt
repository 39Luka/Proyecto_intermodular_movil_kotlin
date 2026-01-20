package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.data.FakeRepository
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState

class PurchasesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        PurchasesUiState(pedidos = FakeRepository.pedidos)
    )
    val uiState: StateFlow<PurchasesUiState> = _uiState

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { state ->
            val filtered = if (newQuery.isBlank()) FakeRepository.pedidos
            else FakeRepository.pedidos.filter { it.fecha.contains(newQuery, ignoreCase = true) }
            state.copy(searchQuery = newQuery, pedidos = filtered)
        }
    }
}
