package net.iesochoa.silvia.projecto_intermodular.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
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
class CatalogViewModelTest {

    private lateinit var viewModel: CatalogViewModel
    private val productRepository: ProductRepository = mockk()
    private val categoryRepository: CategoryRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        coEvery { authRepository.getUser() } returns flowOf(null)
        coEvery { categoryRepository.getCategories() } returns emptyList()
        coEvery { productRepository.getProducts(any(), any(), any(), any(), any()) } returns PagedResponse(emptyList(), 0, 0, 0)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-06: initial_load_fetches_categories_and_products
     * Comprueba que al inicializar el ViewModel se cargan las categorías y productos iniciales.
     */
    @Test
    fun `initial load fetches categories and products`() = runTest {
        val categories = listOf(Category(1, "Panadería"), Category(2, "Bollería"))
        val products = listOf(Product(id = 1, name = "Croissant", price = 1.2, active = true))
        val pagedResponse = PagedResponse(content = products, totalPages = 1, totalElements = 1)

        coEvery { categoryRepository.getCategories() } returns categories
        coEvery { productRepository.getProducts(null, null, 0, 12) } returns pagedResponse

        viewModel = CatalogViewModel(productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(categories, state.categories)
            assertEquals(1, state.products.size)
            assertEquals("Croissant", state.products[0].title)
            assertEquals(false, state.isLoading)
        }
    }

    /**
     * CP-07: onSearchQueryChange_updates_query_and_reloads_products
     * Valida que al cambiar la búsqueda se actualiza el estado y se piden nuevos productos al repositorio.
     */
    @Test
    fun `onSearchQueryChange updates query and reloads products`() = runTest {
        viewModel = CatalogViewModel(productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val query = "Donut"
        coEvery { productRepository.getProducts(null, query, 0, 12) } returns PagedResponse(emptyList(), 0, 0, 0)

        viewModel.onSearchQueryChange(query)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(query, viewModel.uiState.value.searchQuery)
        coVerify { productRepository.getProducts(null, query, 0, 12) }
    }

    /**
     * CP-08: selectCategory_updates_category_and_reloads_products
     * Verifica que al seleccionar una categoría se filtre la lista de productos correctamente.
     */
    @Test
    fun `selectCategory updates category and reloads products`() = runTest {
        viewModel = CatalogViewModel(productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val categoryId = 2
        coEvery { productRepository.getProducts(categoryId, null, 0, 12) } returns PagedResponse(emptyList(), 0, 0, 0)

        viewModel.selectCategory(categoryId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(categoryId, viewModel.uiState.value.selectedCategoryId)
        coVerify { productRepository.getProducts(categoryId, null, 0, 12) }
    }

    /**
     * CP-09: goToNextPage_increments_page_and_reloads
     * Comprueba que la navegación a la siguiente página incrementa el índice y solicita nuevos datos.
     */
    @Test
    fun `goToNextPage increments page and reloads`() = runTest {
        val pagedResponse = PagedResponse<Product>(content = emptyList(), totalPages = 2, totalElements = 0)
        coEvery { productRepository.getProducts(null, null, 0, 12) } returns pagedResponse
        coEvery { productRepository.getProducts(null, null, 1, 12) } returns PagedResponse(emptyList(), 2, 0, 1)

        viewModel = CatalogViewModel(productRepository, categoryRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.goToNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.uiState.value.currentPage)
        coVerify { productRepository.getProducts(null, null, 1, 12) }
    }
}
