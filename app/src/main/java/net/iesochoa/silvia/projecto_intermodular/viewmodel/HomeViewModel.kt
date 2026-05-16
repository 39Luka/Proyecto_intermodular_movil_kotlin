package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.util.Log
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
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.ui.utils.toCurrency
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel que gestiona los datos de la pantalla principal (Home).
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
        observeUser()
    }

    /** Observa los datos del usuario para actualizar la imagen de perfil. */
    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    /** Carga la información inicial: últimas novedades y top ventas. */
    fun loadHomeData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Cargando datos de inicio...")
                val categories = try { categoryRepository.getCategories() } catch (e: Exception) { emptyList() }
                
                // Obtenemos un pool mayor de datos para asegurar que tras filtrar tengamos 4 de cada
                val latest = productRepository.getProducts(page = 0, size = 10).content
                val topSelling = productRepository.getTopSellingProducts(10)

                val latestCards = latest.map { product ->
                    mapProductToCardItem(product, categories)
                }.sortedByDescending { it.id }.take(4)

                val topCards = topSelling.map { product ->
                    mapProductToCardItem(product, categories)
                }.take(4)

                _uiState.update { it.copy(
                    promociones = latestCards,
                    topVentas = topCards,
                    filteredPromociones = latestCards,
                    filteredTopVentas = topCards,
                    isLoading = false,
                    error = null
                ) }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error cargando datos", e)
                val errorMsg = when (e) {
                    is java.net.SocketTimeoutException -> "Tiempo de espera agotado. El servidor tarda demasiado en responder."
                    is java.net.UnknownHostException -> "No hay conexión a internet."
                    else -> "Error: ${e.localizedMessage ?: "Error de conexión"}"
                }
                _uiState.update { it.copy(
                    isLoading = false,
                    error = errorMsg
                ) }
            }
        }
    }

    /** Mapea un producto a un objeto visual CardItem. */
    private fun mapProductToCardItem(product: net.iesochoa.silvia.projecto_intermodular.data.Product, categories: List<net.iesochoa.silvia.projecto_intermodular.data.Category>): CardItem {
        val catName = product.category?.name 
            ?: categories.find { it.id == product.categoryId }?.name 
            ?: "Obrador"
            
        return CardItem(
            id = product.id,
            imageUrl = product.getDisplayImage(),
            title = product.getDisplayTitle(),
            bottomText1 = product.description,
            bottomText2 = product.price.toCurrency(),
            categoryName = catName,
            isOutOfStock = (product.stock ?: 0) <= 0
        )
    }

    /** Filtra las secciones Home según la búsqueda. */
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
