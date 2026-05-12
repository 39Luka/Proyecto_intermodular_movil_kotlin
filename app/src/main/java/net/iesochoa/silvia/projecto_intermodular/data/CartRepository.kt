package net.iesochoa.silvia.projecto_intermodular.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.iesochoa.silvia.projecto_intermodular.model.CartItemState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val apiService: ApiService) {

    private val _items = MutableStateFlow<List<CartItemState>>(emptyList())
    val items: StateFlow<List<CartItemState>> = _items.asStateFlow()

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

    fun updateQuantity(productId: Int, quantity: Int) {
        _items.value = _items.value.map {
            if (it.product.id == productId) it.copy(quantity = quantity.coerceAtLeast(1)) else it
        }
    }

    fun removeItem(productId: Int) {
        _items.value = _items.value.filterNot { it.product.id == productId }
    }

    fun clearCart() {
        _items.value = emptyList()
    }
}
