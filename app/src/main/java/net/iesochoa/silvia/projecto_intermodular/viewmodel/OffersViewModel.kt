// viewmodel/OffersViewModel.kt
package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.data.FakeRepository
import net.iesochoa.silvia.projecto_intermodular.model.OffersUiState

class OffersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        OffersUiState(
            allProducts = FakeRepository.horizontalProducts,
            filteredProducts = FakeRepository.horizontalProducts
        )
    )
    val uiState: StateFlow<OffersUiState> = _uiState

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isBlank()) state.allProducts
            else state.allProducts.filter { it.title.contains(query, ignoreCase = true) }
            state.copy(searchQuery = query, filteredProducts = filtered)
        }
    }
}
