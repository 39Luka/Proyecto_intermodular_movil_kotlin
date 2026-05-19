package net.iesochoa.silvia.projecto_intermodular.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private val productRepository: ProductRepository = mockk()
    private val categoryRepository: CategoryRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock default behaviors for init
        coEvery { categoryRepository.getCategories() } returns emptyList()
        coEvery { productRepository.getProducts(any(), any(), any(), any(), any()) } returns PagedResponse(emptyList(), 0, 0, 0)
        
        viewModel = ProductViewModel(productRepository, categoryRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-24: loadProducts_updates_uiState_with_products
     * Verifica que el listado de productos se cargue correctamente en el estado de la UI desde el repositorio.
     */
    @Test
    fun `loadProducts updates uiState with products`() = runTest {
        val products = listOf(
            Product(id = 1, name = "Pan", price = 1.0, active = true),
            Product(id = 2, name = "Leche", price = 1.5, active = true)
        )
        val pagedResponse = PagedResponse(
            content = products,
            totalPages = 1,
            totalElements = 2,
            number = 0
        )

        coEvery { productRepository.getProducts(categoryId = null, page = 0) } returns pagedResponse

        viewModel.loadProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(products, state.products)
            assertEquals(1, state.totalPages)
            assertEquals(0, state.currentPage)
            assertEquals(false, state.isLoading)
        }
    }

    /**
     * CP-24.1: loadProducts_handles_error
     * Valida que los errores durante la carga de productos se capturen y se muestre un mensaje de error en la UI.
     */
    @Test
    fun `loadProducts handles error`() = runTest {
        val errorMessage = "Error de red"
        coEvery { productRepository.getProducts(any(), any(), any(), any(), any()) } throws Exception(errorMessage)

        viewModel.loadProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
    }
}
