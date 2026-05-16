package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.CategoryRepository
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.model.ProductUiState
import javax.inject.Inject

/**
 * ViewModel para gestionar el listado y detalle de productos.
 */
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var selectedCategoryId: Int? = null

    init {
        loadCategories()
        loadProducts()
    }

    /** Carga las categorías disponibles. */
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = categoryRepository.getCategories()
                _uiState.value = _uiState.value.copy(categories = categories)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al cargar categorías"
                )
            }
        }
    }

    /** Carga los productos con paginación y filtrado opcional de categoría. */
    fun loadProducts(categoryId: Int? = null, page: Int = 0) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        selectedCategoryId = categoryId
        currentPage = page
        
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts(
                    categoryId = categoryId,
                    page = page,
                    size = 12
                )
                _uiState.value = _uiState.value.copy(
                    products = response.content,
                    totalPages = response.totalPages,
                    currentPage = page,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar productos"
                )
            }
        }
    }

    /** Navega a la siguiente página de productos. */
    fun nextPage() {
        if (currentPage < (_uiState.value.totalPages - 1)) {
            loadProducts(selectedCategoryId, currentPage + 1)
        }
    }

    /** Navega a la página anterior de productos. */
    fun previousPage() {
        if (currentPage > 0) {
            loadProducts(selectedCategoryId, currentPage - 1)
        }
    }

    /** Carga el detalle de un producto específico. */
    fun loadProductDetail(id: Int) {
        _uiState.value = _uiState.value.copy(detailLoading = true)
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(id)
                _uiState.value = _uiState.value.copy(
                    selectedProduct = product,
                    detailLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    detailLoading = false,
                    error = e.message ?: "Error al cargar producto"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
