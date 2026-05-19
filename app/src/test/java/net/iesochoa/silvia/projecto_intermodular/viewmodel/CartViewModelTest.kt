package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.util.Log
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.iesochoa.silvia.projecto_intermodular.data.*
import net.iesochoa.silvia.projecto_intermodular.model.CartItemState
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val purchaseRepository: PurchaseRepository = mockk()
    private val promotionRepository: PromotionRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    private val cartRepository: CartRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()
    private val cartItemsFlow = MutableStateFlow<List<CartItemState>>(emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        
        coEvery { authRepository.getUser() } returns flowOf(User(id = 1))
        coEvery { cartRepository.items } returns cartItemsFlow
        coEvery { promotionRepository.getActivePromotions(any(), any()) } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-14: observing_cart_items_updates_uiState_and_calculates_total
     * Verifica que el total se calcule correctamente al observar cambios en los items del carrito.
     */
    @Test
    fun `observing cart items updates uiState and calculates total`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 10.0)
        val cartItems = listOf(CartItemState(product, 2))
        
        viewModel = CartViewModel(purchaseRepository, promotionRepository, authRepository, cartRepository)
        cartItemsFlow.value = cartItems
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.uiState.value.items.size)
        assertEquals(20.0, viewModel.uiState.value.total, 0.001)
    }

    /**
     * CP-15: onPromotionSelected_recalculates_total
     * Valida que al aplicar una promoción seleccionada, el total final se actualice restando el descuento.
     */
    @Test
    fun `onPromotionSelected recalculates total`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 10.0)
        val promotion = Promotion(id = 1, productId = 1, discountPercentage = 10.0)
        
        coEvery { promotionRepository.getActivePromotions(1, any()) } returns listOf(promotion)
        
        val cartItems = listOf(CartItemState(product, 1))
        
        viewModel = CartViewModel(purchaseRepository, promotionRepository, authRepository, cartRepository)
        cartItemsFlow.value = cartItems
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onPromotionSelected(1, 1)
        
        assertEquals(9.0, viewModel.uiState.value.total, 0.001)
    }

    /**
     * CP-16: checkout_success_calls_onSuccess_and_clears_cart
     * Verifica que el proceso de checkout cree la compra y vacíe el carrito al finalizar con éxito.
     */
    @Test
    fun `checkout success calls onSuccess and clears cart`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 10.0)
        val cartItems = listOf(CartItemState(product, 1))
        var successCalled = false

        coEvery { purchaseRepository.createPurchase(any(), 1) } returns mockk()
        coEvery { cartRepository.clearCart() } returns Unit

        viewModel = CartViewModel(purchaseRepository, promotionRepository, authRepository, cartRepository)
        cartItemsFlow.value = cartItems
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.checkout { successCalled = true }
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, successCalled)
        coVerify { purchaseRepository.createPurchase(any(), 1) }
        coVerify { cartRepository.clearCart() }
    }

    /**
     * CP-14.2: calculateTotal_with_empty_cart_is_zero
     * Verifica que si el carrito no tiene elementos, el total sea exactamente 0.0.
     */
    @Test
    fun `calculateTotal with empty cart is zero`() = runTest {
        viewModel = CartViewModel(purchaseRepository, promotionRepository, authRepository, cartRepository)
        cartItemsFlow.value = emptyList()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(0.0, viewModel.uiState.value.total, 0.001)
    }

    /**
     * CP-15.1: onPromotionSelected_updates_total_correctly_when_switching_promos
     * Valida que al cambiar entre diferentes promociones, el total se recalcule correctamente eliminando el descuento anterior.
     */
    @Test
    fun `onPromotionSelected updates total correctly when switching promos`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 10.0)
        val promo1 = Promotion(id = 1, productId = 1, discountPercentage = 10.0)
        val promo2 = Promotion(id = 2, productId = 1, discountPercentage = 20.0)
        
        coEvery { promotionRepository.getActivePromotions(1, any()) } returns listOf(promo1, promo2)
        
        viewModel = CartViewModel(purchaseRepository, promotionRepository, authRepository, cartRepository)
        cartItemsFlow.value = listOf(CartItemState(product, 1))
        testDispatcher.scheduler.advanceUntilIdle()

        // Aplicar 10% -> 9.0
        viewModel.onPromotionSelected(1, 1)
        assertEquals(9.0, viewModel.uiState.value.total, 0.001)

        // Cambiar a 20% -> 8.0
        viewModel.onPromotionSelected(1, 2)
        assertEquals(8.0, viewModel.uiState.value.total, 0.001)
    }

    /**
     * CP-16.3: checkout_network_error_shows_error_message
     * Comprueba que si falla la red durante el proceso de compra, el error se capture y se muestre al usuario.
     */
    @Test
    fun `checkout network error shows error message`() = runTest {
        val product = Product(id = 1, name = "Pan", price = 10.0)
        coEvery { purchaseRepository.createPurchase(any(), any()) } throws java.net.UnknownHostException()

        viewModel = CartViewModel(purchaseRepository, promotionRepository, authRepository, cartRepository)
        cartItemsFlow.value = listOf(CartItemState(product, 1))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.checkout { }
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("No se pudo conectar al servidor. Revisa tu conexión a internet.", viewModel.uiState.value.error)
    }
}
