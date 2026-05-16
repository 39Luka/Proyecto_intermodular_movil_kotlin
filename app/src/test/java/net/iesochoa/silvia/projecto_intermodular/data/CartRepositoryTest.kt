package net.iesochoa.silvia.projecto_intermodular.data

import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CartRepositoryTest {

    private lateinit var repository: CartRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setup() {
        repository = CartRepository(apiService)
    }

    /**
     * CP-13.1: addToCart_adds_new_product_to_items
     * Verifica que al añadir un producto nuevo al repositorio del carrito, este se incluya en la lista.
     */
    @Test
    fun `addToCart adds new product to items`() {
        val product = Product(id = 1, name = "Pan")
        
        repository.addToCart(product, 2)
        
        val items = repository.items.value
        assertEquals(1, items.size)
        assertEquals(1, items[0].product.id)
        assertEquals(2, items[0].quantity)
    }

    /**
     * CP-13.2: addToCart_increments_quantity_if_product_exists
     * Comprueba que añadir un producto que ya estaba en el carrito incremente su cantidad en lugar de duplicarlo.
     */
    @Test
    fun `addToCart increments quantity if product exists`() {
        val product = Product(id = 1, name = "Pan")
        
        repository.addToCart(product, 2)
        repository.addToCart(product, 3)
        
        val items = repository.items.value
        assertEquals(1, items.size)
        assertEquals(5, items[0].quantity)
    }

    /**
     * CP-14.1: removeItem_removes_correct_product
     * Valida que se elimine el producto correcto de la lista del carrito al llamar a la función de borrado.
     */
    @Test
    fun `removeItem removes correct product`() {
        val p1 = Product(id = 1, name = "A")
        val p2 = Product(id = 2, name = "B")
        
        repository.addToCart(p1, 1)
        repository.addToCart(p2, 1)
        repository.removeItem(1)
        
        val items = repository.items.value
        assertEquals(1, items.size)
        assertEquals(2, items[0].product.id)
    }

    /**
     * CP-16.2: clearCart_empties_the_list
     * Verifica que la función de limpiar el carrito elimine todos los elementos almacenados.
     */
    @Test
    fun `clearCart empties the list`() {
        repository.addToCart(Product(id = 1), 1)
        repository.clearCart()
        assertEquals(0, repository.items.value.size)
    }
}
