package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.util.Log
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val productRepository: ProductRepository = mockk()
    private val categoryRepository: CategoryRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        
        coEvery { authRepository.getUser() } returns flowOf(null)
        coEvery { categoryRepository.getCategories() } returns emptyList()
        coEvery { productRepository.getProducts(any(), any(), any(), any(), any()) } returns PagedResponse(emptyList())
        coEvery { productRepository.getTopSellingProducts(any()) } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-25: loadHomeData_updates_state_with_products
     * Verifica que la pantalla de inicio cargue correctamente las novedades y los productos más vendidos.
     */
    @Test
    fun `loadHomeData updates state with products`() = runTest {
        val products = listOf(Product(id = 1, name = "Novedad", price = 1.0))
        val topSelling = listOf(Product(id = 2, name = "Top", price = 2.0))
        
        coEvery { productRepository.getProducts(any(), any(), any(), any(), any()) } returns PagedResponse(content = products)
        coEvery { productRepository.getTopSellingProducts(any()) } returns topSelling

        viewModel = HomeViewModel(productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.uiState.value.promociones.size)
        assertEquals(1, viewModel.uiState.value.topVentas.size)
        assertEquals("Novedad", viewModel.uiState.value.promociones[0].title)
        assertEquals("Top", viewModel.uiState.value.topVentas[0].title)
    }

    /**
     * CP-25.1: onSearchQueryChange_filters_home_sections
     * Valida que la búsqueda en la Home filtre correctamente tanto las novedades como los productos más vendidos.
     */
    @Test
    fun `onSearchQueryChange filters home sections`() = runTest {
        val products = listOf(Product(id = 1, name = "Croissant", price = 1.0))
        coEvery { productRepository.getProducts(any(), any(), any(), any(), any()) } returns PagedResponse(content = products)

        viewModel = HomeViewModel(productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChange("Cro")
        assertEquals(1, viewModel.uiState.value.filteredPromociones.size)

        viewModel.onSearchQueryChange("Donut")
        assertEquals(0, viewModel.uiState.value.filteredPromociones.size)
    }
}
