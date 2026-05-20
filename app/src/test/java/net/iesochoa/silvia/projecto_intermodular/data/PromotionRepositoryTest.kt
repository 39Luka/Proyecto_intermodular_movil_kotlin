package net.iesochoa.silvia.projecto_intermodular.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PromotionRepositoryTest {

    private lateinit var repository: PromotionRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setup() {
        repository = PromotionRepository(apiService)
    }

    /**
     * CP-10.2: getActivePromotions_filters_only_active_ones
     * Valida que el repositorio de promociones ignore las ofertas que ya han caducado o están inactivas.
     */
    @Test
    fun `getActivePromotions filters only active ones`() = runTest {
        val promotions = listOf(
            Promotion(id = 1, active = true, productId = 10),
            Promotion(id = 2, active = false, productId = 11)
        )
        coEvery { apiService.getActivePromotions(any(), any(), any(), any()) } returns PagedResponse(content = promotions)

        val result = repository.getActivePromotions()

        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals(true, result[0].active)
    }

    /**
     * CP-10.3: getAllPromotions_also_filters_only_active_ones
     * Comprueba que la obtención de todas las promociones del sistema también aplique el filtro de actividad.
     */
    @Test
    fun `getAllPromotions also filters only active ones`() = runTest {
        val promotions = listOf(
            Promotion(id = 3, active = true, productId = 12)
        )
        coEvery { apiService.getAllPromotions(any(), any()) } returns PagedResponse(content = promotions)

        val result = repository.getAllPromotions()

        assertEquals(1, result.size)
    }
}
