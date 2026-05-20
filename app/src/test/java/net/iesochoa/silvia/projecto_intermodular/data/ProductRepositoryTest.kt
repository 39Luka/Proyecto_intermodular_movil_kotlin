package net.iesochoa.silvia.projecto_intermodular.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {

    private lateinit var repository: ProductRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setup() {
        repository = ProductRepository(apiService)
    }

    /**
     * CP-20: getProducts_filters_only_active_products
     * Comprueba que el repositorio filtre los productos inactivos antes de entregarlos a la capa superior.
     */
    @Test
    fun `getProducts filters only active products`() = runTest {
        val allProducts = listOf(
            Product(id = 1, name = "Active", active = true),
            Product(id = 2, name = "Inactive", active = false)
        )
        val response = PagedResponse(content = allProducts, totalPages = 1)

        coEvery { apiService.getProducts(any(), any(), any(), any(), any()) } returns response

        val result = repository.getProducts()

        assertEquals(1, result.content.size)
        assertEquals("Active", result.content[0].name)
        assertTrue(result.content[0].active)
    }

    /**
     * CP-20.1: getProductById_throws_exception_if_product_is_inactive
     * Verifica que el sistema no permita acceder al detalle de productos que ya no están disponibles (activos).
     */
    @Test
    fun `getProductById throws exception if product is inactive`() = runTest {
        val inactiveProduct = Product(id = 1, name = "Old", active = false)
        coEvery { apiService.getProductById(1) } returns inactiveProduct

        try {
            repository.getProductById(1)
            assertTrue("Should have thrown exception", false)
        } catch (e: Exception) {
            assertEquals("Este producto ya no está disponible", e.message)
        }
    }
}
