package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var allProductsList: List<CardItem> = emptyList()

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState

    init {
        loadProducts()
    }

    fun loadProducts() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val products = productRepository.getProducts(null, 0, 50).content
                val cards = products.map { product ->
                    CardItem(
                        id = product.id,
                        imageUrl = product.getDisplayImage(),
                        title = product.getDisplayTitle(),
                        bottomText1 = product.description,
                        bottomText2 = "€${String.format(Locale.US, "%.2f", product.price ?: 0.0)}",
                        categoryName = product.getCategoryName()
                    )
                }
                allProductsList = cards
                _uiState.update { it.copy(
                    products = cards,
                    filteredProducts = cards,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Error al cargar el catálogo. Comprueba tu conexión."
                ) }
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { current ->
            val filtered = if (newQuery.isBlank()) allProductsList
            else allProductsList.filter { it.title.contains(newQuery, ignoreCase = true) }
            current.copy(
                searchQuery = newQuery,
                filteredProducts = filtered
            )
        }
    }
}
