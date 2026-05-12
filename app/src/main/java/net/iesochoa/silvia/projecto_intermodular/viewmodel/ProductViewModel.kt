package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.Category
import net.iesochoa.silvia.projecto_intermodular.data.CategoryRepository
import net.iesochoa.silvia.projecto_intermodular.data.Product
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import javax.inject.Inject

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

    fun loadProducts(categoryId: Int? = null, page: Int = 0) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        selectedCategoryId = categoryId
        currentPage = page
        
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts(categoryId, page, 12)
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

    fun nextPage() {
        if (currentPage < (_uiState.value.totalPages - 1)) {
            loadProducts(selectedCategoryId, currentPage + 1)
        }
    }

    fun previousPage() {
        if (currentPage > 0) {
            loadProducts(selectedCategoryId, currentPage - 1)
        }
    }

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

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val detailLoading: Boolean = false,
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val error: String? = null
)
