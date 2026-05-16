package net.iesochoa.silvia.projecto_intermodular.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.iesochoa.silvia.projecto_intermodular.data.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OffersViewModelTest {

    private lateinit var viewModel: OffersViewModel
    private val promotionRepository: PromotionRepository = mockk()
    private val productRepository: ProductRepository = mockk()
    private val categoryRepository: CategoryRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        coEvery { authRepository.getUser() } returns flowOf(User(id = 1))
        coEvery { categoryRepository.getCategories() } returns emptyList()
        coEvery { promotionRepository.getAvailablePromotions(any()) } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-10: loadPromotions_fetches_and_maps_promotions
     * Verifica que el ViewModel cargue las promociones activas y las asocie correctamente con sus productos.
     */
    @Test
    fun `loadPromotions fetches and maps promotions`() = runTest {
        val promotions = listOf(
            Promotion(id = 1, description = "Oferta 1", productId = 10, discountPercentage = 10.0)
        )
        val product = Product(id = 10, name = "Pan", price = 1.0)
        
        coEvery { promotionRepository.getAvailablePromotions(1) } returns promotions
        coEvery { productRepository.getProductById(10) } returns product

        viewModel = OffersViewModel(promotionRepository, productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(1, state.filteredProducts.size)
            assertEquals("Pan", state.filteredProducts[0].title)
            assertEquals("Oferta 1", state.filteredProducts[0].description)
            assertEquals("-10.0%", state.filteredProducts[0].rightValue)
        }
    }

    /**
     * CP-11: onSearchQueryChange_filters_items_locally
     * Comprueba que la búsqueda en la pantalla de ofertas filtre los items disponibles de forma instantánea.
     */
    @Test
    fun `onSearchQueryChange filters items locally`() = runTest {
        val promotions = listOf(
            Promotion(id = 1, description = "Promo A", productId = 10),
            Promotion(id = 2, description = "Promo B", productId = 11)
        )
        coEvery { promotionRepository.getAvailablePromotions(any()) } returns promotions
        coEvery { productRepository.getProductById(10) } returns Product(id = 10, name = "Apple")
        coEvery { productRepository.getProductById(11) } returns Product(id = 11, name = "Banana")

        viewModel = OffersViewModel(promotionRepository, productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChange("Apple")
        
        assertEquals(1, viewModel.uiState.value.filteredProducts.size)
        assertEquals("Apple", viewModel.uiState.value.filteredProducts[0].title)
    }

    /**
     * CP-10.1: loadPromotions_handles_errors_gracefully
     * Valida que si falla la carga de promociones, se muestre un mensaje de error amigable al usuario.
     */
    @Test
    fun `loadPromotions handles errors gracefully`() = runTest {
        coEvery { promotionRepository.getAvailablePromotions(any()) } throws Exception("API Error")

        viewModel = OffersViewModel(promotionRepository, productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("No se han podido cargar las ofertas activas.", viewModel.uiState.value.error)
    }
}
