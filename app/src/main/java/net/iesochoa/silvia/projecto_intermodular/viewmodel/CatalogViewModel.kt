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
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import java.util.Locale
import javax.inject.Inject

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

    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

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

    private fun loadProducts() {
        val pageSize = _uiState.value.pageSize
        val currentPage = _uiState.value.currentPage
        val categoryId = _uiState.value.selectedCategoryId
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts(categoryId, currentPage, pageSize)
                totalProducts = response.totalElements?.toInt() ?: 0
                val totalPages = response.totalPages ?: 1
                
                val products = response.content
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

    fun showFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = true) }
    }

    fun hideFilterDialog() {
        _uiState.update { it.copy(showFilterDialog = false) }
    }

    fun selectCategory(categoryId: Int?) {
        _uiState.update { it.copy(
            selectedCategoryId = categoryId,
            currentPage = 0
        ) }
        loadProducts()
    }

    fun goToNextPage() {
        val currentPage = _uiState.value.currentPage
        val totalPages = _uiState.value.totalPages
        if (currentPage < totalPages - 1) {
            _uiState.update { it.copy(currentPage = currentPage + 1) }
            loadProducts()
        }
    }

    fun goToPreviousPage() {
        val currentPage = _uiState.value.currentPage
        if (currentPage > 0) {
            _uiState.update { it.copy(currentPage = currentPage - 1) }
            loadProducts()
        }
    }
}
