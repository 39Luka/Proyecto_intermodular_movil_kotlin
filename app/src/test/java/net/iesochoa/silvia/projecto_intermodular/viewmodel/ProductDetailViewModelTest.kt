package net.iesochoa.silvia.projecto_intermodular.viewmodel

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
class ProductDetailViewModelTest {

    private lateinit var viewModel: ProductDetailViewModel
    private val productRepository: ProductRepository = mockk()
    private val cartRepository: CartRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { authRepository.getUser() } returns flowOf(null)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-12: loadProduct_success_updates_state
     * Verifica que al cargar el detalle de un producto, la UI reciba los datos correctamente del repositorio.
     */
    @Test
    fun `loadProduct success updates state`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 1.0)
        coEvery { productRepository.getProductById(1) } returns product

        viewModel = ProductDetailViewModel(productRepository, cartRepository, authRepository)
        viewModel.loadProduct(1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(product, viewModel.uiState.value.product)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    /**
     * CP-13: addToCart_calls_repository_and_resets_quantity
     * Valida que añadir un producto al carrito desde el detalle envíe la cantidad correcta y resetee el contador.
     */
    @Test
    fun `addToCart calls repository and resets quantity`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 1.0)
        coEvery { productRepository.getProductById(1) } returns product
        coEvery { cartRepository.addToCart(product, 2) } returns Unit

        viewModel = ProductDetailViewModel(productRepository, cartRepository, authRepository)
        viewModel.loadProduct(1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.updateQuantity(2)
        viewModel.addToCart()

        coVerify { cartRepository.addToCart(product, 2) }
        assertEquals(1, viewModel.uiState.value.quantity)
        assertEquals(true, viewModel.uiState.value.addedToCart)
    }
}
