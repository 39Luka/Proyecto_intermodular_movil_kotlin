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
class PurchasesViewModelTest {

    private lateinit var viewModel: PurchasesViewModel
    private val purchaseRepository: PurchaseRepository = mockk()
    private val authRepository: AuthRepository = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        coEvery { authRepository.getUser() } returns flowOf(User(id = 1))
        coEvery { purchaseRepository.getPurchases(any(), any(), any(), any(), any()) } returns PagedResponse(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * CP-17: loadPurchases_fetches_and_maps_purchases_for_logged_user
     * Verifica que el historial de compras se cargue y formatee correctamente para el usuario autenticado.
     */
    @Test
    fun `loadPurchases fetches and maps purchases for logged user`() = runTest {
        val purchases = listOf(
            Purchase(id = 1, userId = 1, total = 20.0, createdAt = "2023-10-27T10:00:00Z", status = "COMPLETADO")
        )
        coEvery { purchaseRepository.getPurchases(1, 0, 10, null, null) } returns PagedResponse(content = purchases, totalPages = 1)

        viewModel = PurchasesViewModel(purchaseRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(1, state.pedidos.size)
            assertEquals("2023-10-27", state.pedidos[0].fecha)
            assertEquals("COMPLETADO", state.pedidos[0].estado)
            assertEquals("€20.00", state.pedidos[0].total)
        }
    }

    /**
     * CP-17.1: onDateRangeSelected_updates_state_and_reloads
     * Comprueba que al seleccionar un rango de fechas, la UI se actualice y solicite los datos filtrados a la API.
     */
    @Test
    fun `onDateRangeSelected updates state and reloads`() = runTest {
        viewModel = PurchasesViewModel(purchaseRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val start = 1698364800000L // 2023-10-27
        val end = 1698451200000L   // 2023-10-28
        
        coEvery { purchaseRepository.getPurchases(1, 0, 10, "2023-10-27", "2023-10-28") } returns PagedResponse(emptyList())

        viewModel.onDateRangeSelected(start, end)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(start, viewModel.uiState.value.startDate)
        assertEquals(end, viewModel.uiState.value.endDate)
    }

    /**
     * CP-17.2: loadPurchases_error_sets_error_message
     * Valida que los errores de red en la carga del historial se comuniquen al usuario mediante un mensaje.
     */
    @Test
    fun `loadPurchases error sets error message`() = runTest {
        coEvery { purchaseRepository.getPurchases(any(), any(), any(), any(), any()) } throws Exception("API Error")

        viewModel = PurchasesViewModel(purchaseRepository, authRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("No se pudo cargar el historial de pedidos.", viewModel.uiState.value.error)
    }
}
