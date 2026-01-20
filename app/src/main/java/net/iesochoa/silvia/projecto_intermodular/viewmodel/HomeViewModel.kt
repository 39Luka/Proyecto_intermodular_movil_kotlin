package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.data.FakeRepository
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            promociones = FakeRepository.promociones,
            topVentas = FakeRepository.topVentas,
            filteredPromociones = FakeRepository.promociones,
            filteredTopVentas = FakeRepository.topVentas
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { state ->
            val filteredProm = if (newQuery.isBlank()) state.promociones
            else state.promociones.filter { it.title.contains(newQuery, ignoreCase = true) }

            val filteredTop = if (newQuery.isBlank()) state.topVentas
            else state.topVentas.filter { it.title.contains(newQuery, ignoreCase = true) }

            state.copy(
                searchQuery = newQuery,
                filteredPromociones = filteredProm,
                filteredTopVentas = filteredTop
            )
        }
    }
}

