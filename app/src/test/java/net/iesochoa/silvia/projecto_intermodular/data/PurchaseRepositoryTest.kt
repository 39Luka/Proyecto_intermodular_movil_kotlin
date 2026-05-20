package net.iesochoa.silvia.projecto_intermodular.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PurchaseRepositoryTest {

    private lateinit var repository: PurchaseRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setup() {
        repository = PurchaseRepository(apiService)
    }

    /**
     * CP-17.3: getPurchases_returns_paged_response_from_api
     * Verifica que el repositorio reciba y entregue correctamente la respuesta paginada del historial de compras.
     */
    @Test
    fun `getPurchases returns paged response from api`() = runTest {
        val purchases = listOf(Purchase(id = 1, userId = 1, total = 100.0))
        val response = PagedResponse(content = purchases, totalPages = 1)

        coEvery { apiService.getPurchases(any(), any(), any(), any(), any()) } returns response

        val result = repository.getPurchases(userId = 1)

        assertEquals(1, result.content.size)
        assertEquals(1, result.content[0].id)
        assertEquals(100.0, result.content[0].total)
    }

    /**
     * CP-16.1: createPurchase_sends_correct_request
     * Comprueba que la creación de una nueva compra en el repositorio genere la llamada correcta a la API.
     */
    @Test
    fun `createPurchase sends correct request`() = runTest {
        val items = listOf(PurchaseItemRequest(productId = 1, quantity = 2))
        val expectedPurchase = Purchase(id = 10, userId = 1, items = emptyList())
        
        coEvery { apiService.createPurchase(any()) } returns expectedPurchase

        val result = repository.createPurchase(items, userId = 1)

        assertEquals(10, result.id)
        assertEquals(1, result.userId)
    }
}
