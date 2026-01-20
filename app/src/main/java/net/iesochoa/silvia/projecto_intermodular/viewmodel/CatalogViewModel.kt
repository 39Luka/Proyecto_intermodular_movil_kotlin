package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.data.FakeRepository
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState

class CatalogViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        CatalogUiState(
            products = FakeRepository.cardProducts,
            filteredProducts = FakeRepository.cardProducts
        )
    )
    val uiState: StateFlow<CatalogUiState> = _uiState

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { current ->
            val filtered = if (newQuery.isBlank()) current.products
            else current.products.filter { it.title.contains(newQuery, ignoreCase = true) }
            current.copy(
                searchQuery = newQuery,
                filteredProducts = filtered
            )
        }
    }
}
