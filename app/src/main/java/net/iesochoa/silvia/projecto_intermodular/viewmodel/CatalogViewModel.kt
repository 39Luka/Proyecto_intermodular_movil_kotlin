package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.data.CategoryRepository
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.model.CardItem
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState
import net.iesochoa.silvia.projecto_intermodular.ui.utils.toCurrency
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel responsable de la lógica del catálogo de productos.
 */
@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var allProductsList: List<CardItem> = emptyList()
    private var totalProducts: Int = 0

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
        loadProducts()
        observeUser()
    }

    /** Observa cambios en el usuario para actualizar la imagen de perfil. */
    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    /** Carga las categorías disponibles para el filtrado. */
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = categoryRepository.getCategories()
                _uiState.update { it.copy(categories = categories) }
            } catch (e: Exception) {
                android.util.Log.e("CatalogVM", "Error loading categories: ${e.message}")
            }
        }
    }

    /** Carga los productos aplicando paginación y filtros. */
    private fun loadProducts() {
        val pageSize = _uiState.value.pageSize
        val currentPage = _uiState.value.currentPage
        val categoryId = _uiState.value.selectedCategoryId
        val searchQuery = _uiState.value.searchQuery
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts(
                    categoryId = categoryId,
                    name = if (searchQuery.isNotBlank()) searchQuery else null,
                    page = currentPage,
                    size = pageSize
                )
                totalProducts = response.totalElements?.toInt() ?: 0
                val totalPages = response.totalPages ?: 1
                
                val products = response.content
                val categories = _uiState.value.categories
                
                val cards = products.map { product ->
                    val catName = product.category?.name 
                        ?: categories.find { it.id == product.categoryId }?.name 
                        ?: "Obrador"

                    CardItem(
                        id = product.id,
                        imageUrl = product.getDisplayImage(),
                        title = product.getDisplayTitle(),
                        bottomText1 = product.description,
                        bottomText2 = product.price.toCurrency(),
                        categoryName = catName,
                        isOutOfStock = (product.stock ?: 0) <= 0
                    )
                }
                allProductsList = cards
                _uiState.update { it.copy(
                    products = cards,
                    filteredProducts = cards,
                    isLoading = false,
                    totalPages = totalPages
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Error al cargar el catálogo. Comprueba tu conexión."
                ) }
            }
        }
    }

    /** Filtra los productos mediante la API según la búsqueda. */
    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { it.copy(
            searchQuery = newQuery,
            currentPage = 0 // Reiniciamos a la primera página al buscar
        ) }
        loadProducts()
    }

    /** Muestra el diálogo de filtros de categoría. */
    fun showFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = true) }
    }

    /** Oculta el diálogo de filtros. */
    fun hideFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = false) }
    }

    /** Selecciona una categoría y recarga los productos. */
    fun selectCategory(categoryId: Int?) {
        _uiState.update { it.copy(
            selectedCategoryId = categoryId,
            currentPage = 0
        ) }
        loadProducts()
    }

    /** Cambia a la siguiente página del catálogo. */
    fun goToNextPage() {
        val currentPage = _uiState.value.currentPage
        val totalPages = _uiState.value.totalPages
        if (currentPage < totalPages - 1) {
            _uiState.update { it.copy(currentPage = currentPage + 1) }
            loadProducts()
        }
    }

    /** Regresa a la página anterior del catálogo. */
    fun goToPreviousPage() {
        val currentPage = _uiState.value.currentPage
        if (currentPage > 0) {
            _uiState.update { it.copy(currentPage = currentPage - 1) }
            loadProducts()
        }
    }
}
