package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.data.PromotionRepository
import net.iesochoa.silvia.projecto_intermodular.model.OffersUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val promotionRepository: PromotionRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OffersUiState())
    val uiState: StateFlow<OffersUiState> = _uiState.asStateFlow()

    private var allOffersList: List<HorizontalCardItem> = emptyList()

    init {
        loadPromotions()
    }

    fun loadPromotions() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val products = productRepository.getProducts(null, 0, 100).content
                val offerItems = mutableListOf<HorizontalCardItem>()
                
                for (product in products) {
                    try {
                        val promos = promotionRepository.getActivePromotions(product.id)
                        for (promo in promos) {
                            offerItems.add(
                                HorizontalCardItem(
                                    id = product.id,
                                    title = product.getDisplayTitle(),
                                    description = promo.description ?: "",
                                    rightLabel = "Descuento",
                                    rightValue = "-${promo.discountPercentage}%",
                                    imageUrl = product.getDisplayImage(),
                                    categoryName = product.getCategoryName()
                                )
                            )
                        }
                    } catch (e: Exception) {}
                }

                allOffersList = offerItems
                _uiState.update { it.copy(
                    allProducts = offerItems,
                    filteredProducts = offerItems,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "No se han podido cargar las ofertas activas."
                ) }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        val filtered = if (query.isBlank()) {
            allOffersList
        } else {
            allOffersList.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }
        _uiState.update { it.copy(
            searchQuery = query,
            filteredProducts = filtered
        ) }
    }
}
