package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Cargando datos de inicio...")
                val latest = productRepository.getProducts(null, 0, 6).content
                
                val topSelling = productRepository.getTopSellingProducts(6)

                val latestCards = latest.map { product ->
                    CardItem(
                        id = product.id,
                        imageUrl = product.getDisplayImage(),
                        title = product.getDisplayTitle(),
                        bottomText1 = product.description,
                        bottomText2 = "€${String.format(Locale.US, "%.2f", product.price ?: 0.0)}",
                        categoryName = product.getCategoryName()
                    )
                }

                val topCards = topSelling.map { product ->
                    CardItem(
                        id = product.id,
                        imageUrl = product.getDisplayImage(),
                        title = product.getDisplayTitle(),
                        bottomText1 = product.description,
                        bottomText2 = "€${String.format(Locale.US, "%.2f", product.price ?: 0.0)}",
                        categoryName = product.getCategoryName()
                    )
                }

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
