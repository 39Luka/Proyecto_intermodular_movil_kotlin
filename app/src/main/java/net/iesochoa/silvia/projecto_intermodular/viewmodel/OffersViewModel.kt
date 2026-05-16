package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.data.CategoryRepository
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.data.PromotionRepository
import net.iesochoa.silvia.projecto_intermodular.model.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.model.OffersUiState
import javax.inject.Inject

/**
 * ViewModel encargado de la gestión y visualización de ofertas y promociones.
 */
@HiltViewModel
class OffersViewModel @Inject constructor(
    private val promotionRepository: PromotionRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OffersUiState())
    val uiState: StateFlow<OffersUiState> = _uiState.asStateFlow()

    private var allOffersList: List<HorizontalCardItem> = emptyList()

    init {
        loadPromotions()
        observeUser()
    }

    /** Observa cambios en el usuario autenticado para actualizar la imagen de perfil. */
    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    /** Carga la lista de promociones activas. */
    private fun loadPromotions() {
        val pageSize = _uiState.value.pageSize
        val currentPage = _uiState.value.currentPage
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                // 1. Cargamos las promociones activas y no usadas para el usuario actual
                val categories = try { categoryRepository.getCategories() } catch (_: Exception) { emptyList() }
                val user = authRepository.getUser().first()
                
                // Usamos el nuevo endpoint de promociones disponibles (no usadas)
                val promotions = promotionRepository.getAvailablePromotions(userId = user?.id)
                
                // 2. Mapeamos a items visuales
                val offerItems = promotions.mapNotNull { promo ->
                    try {
                        val product = productRepository.getProductById(promo.productId)
                        val catName = product.category?.name 
                            ?: categories.find { it.id == product.categoryId }?.name 
                            ?: "Obrador"

                        HorizontalCardItem(
                            id = product.id,
                            title = product.getDisplayTitle(),
                            description = promo.description ?: "",
                            rightLabel = "Descuento",
                            rightValue = "-${promo.discountPercentage}%",
                            imageUrl = product.getDisplayImage(),
                            categoryName = catName,
                            isUsed = promo.used
                        )
                    } catch (e: Exception) {
                        null
                    }
                }

                // 3. Aplicamos paginación local sobre la lista de ofertas reales
                val totalItems = offerItems.size
                val totalPages = if (totalItems == 0) 1 else (totalItems + pageSize - 1) / pageSize
                
                // Ajustamos la página actual si fuera necesario
                val adjustedPage = if (currentPage >= totalPages) 0 else currentPage
                
                val startIndex = adjustedPage * pageSize
                val endIndex = (startIndex + pageSize).coerceAtMost(totalItems)
                val pagedOffers = if (totalItems > 0) offerItems.subList(startIndex, endIndex) else emptyList()

                allOffersList = offerItems
                _uiState.update { it.copy(
                    allProducts = offerItems,
                    filteredProducts = pagedOffers,
                    isLoading = false,
                    totalPages = totalPages,
                    currentPage = adjustedPage
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "No se han podido cargar las ofertas activas."
                ) }
            }
        }
    }

    /** Filtra las ofertas según la búsqueda. */
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

    /** Navega a la siguiente página de resultados del catálogo de promociones. */
    fun goToNextPage() {
        val currentPage = _uiState.value.currentPage
        val totalPages = _uiState.value.totalPages
        if (currentPage < totalPages - 1) {
            _uiState.update { it.copy(currentPage = currentPage + 1) }
            loadPromotions()
        }
    }

    /** Navega a la página anterior de resultados. */
    fun goToPreviousPage() {
        val currentPage = _uiState.value.currentPage
        if (currentPage > 0) {
            _uiState.update { it.copy(currentPage = currentPage - 1) }
            loadPromotions()
        }
    }
}
