package net.iesochoa.silvia.projecto_intermodular.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iesochoa.silvia.projecto_intermodular.model.CartItemState
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio que gestiona el estado del carrito de compras.
 */
@Singleton
class CartRepository @Inject constructor(private val apiService: ApiService) {

    private val _items = MutableStateFlow<List<CartItemState>>(emptyList())
    /** Flujo observable con la lista de productos en el carrito. */
    val items: StateFlow<List<CartItemState>> = _items.asStateFlow()

    /** Añade un producto al carrito o incrementa su cantidad si ya existe. */
    fun addToCart(product: Product, quantity: Int) {
        val current = _items.value.toMutableList()
        val existing = current.find { it.product.id == product.id }
        if (existing != null) {
            val index = current.indexOf(existing)
            current[index] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            current.add(CartItemState(product, quantity))
        }
        _items.value = current
    }

    /** Actualiza la cantidad de un producto específico en el carrito. */
    fun updateQuantity(productId: Int, quantity: Int) {
        _items.value = _items.value.map {
            if (it.product.id == productId) it.copy(quantity = quantity.coerceAtLeast(1)) else it
        }
    }

    /** Elimina un producto del carrito. */
    fun removeItem(productId: Int) {
        _items.value = _items.value.filterNot { it.product.id == productId }
    }

    /** Vacía todos los elementos del carrito. */
    fun clearCart() {
        _items.value = emptyList()
    }
}
