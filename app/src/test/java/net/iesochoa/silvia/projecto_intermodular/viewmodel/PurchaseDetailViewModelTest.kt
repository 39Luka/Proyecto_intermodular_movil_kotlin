package net.iesochoa.silvia.projecto_intermodular.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.iesochoa.silvia.projecto_intermodular.data.Purchase
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PurchaseDetailViewModelTest {

    private lateinit var viewModel: PurchaseDetailViewModel
    private val purchaseRepository: PurchaseRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PurchaseDetailViewModel(purchaseRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-26: loadPurchase_success_updates_state
     * Verifica que al cargar el detalle de una compra, la UI reciba los datos correctos del pedido.
     */
    @Test
    fun `loadPurchase success updates state`() = runTest {
        val purchase = Purchase(id = 1, userId = 1, total = 50.0)
        coEvery { purchaseRepository.getPurchaseById(1) } returns purchase

        viewModel.loadPurchase(1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(purchase, viewModel.uiState.value.purchase)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    /**
     * CP-26.1: cancelPurchase_calls_repository_and_reloads
     * Comprueba que la acción de cancelar un pedido llame al repositorio y refresque los datos de la compra.
     */
    @Test
    fun `cancelPurchase calls repository and reloads`() = runTest {
        val purchase = Purchase(id = 1, userId = 1, status = "CREATED")
        coEvery { purchaseRepository.getPurchaseById(1) } returns purchase
        coEvery { purchaseRepository.cancelPurchase(1) } returns Unit

        viewModel.loadPurchase(1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.cancelPurchase()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { purchaseRepository.cancelPurchase(1) }
        coVerify(exactly = 2) { purchaseRepository.getPurchaseById(1) }
    }
}
