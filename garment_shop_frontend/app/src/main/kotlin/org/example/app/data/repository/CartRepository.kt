package org.example.app.data.repository

import org.example.app.data.model.CartItem
import org.example.app.data.model.OrderSummary
import org.example.app.data.model.Product
import org.example.app.data.remote.ApiService

/**
 * PUBLIC_INTERFACE
 * Repository for cart operations and checkout.
 */
class CartRepository(
    private val api: ApiService
) {
    // In-memory cart. For production, persist via database or shared prefs.
    private val cart = linkedMapOf<String, CartItem>()

    /** PUBLIC_INTERFACE
     * Get current cart items.
     */
    fun getCartItems(): List<CartItem> = cart.values.toList()

    /** PUBLIC_INTERFACE
     * Add product to cart or increase quantity.
     */
    fun addToCart(product: Product, qty: Int = 1) {
        val existing = cart[product.id]
        if (existing == null) {
            cart[product.id] = CartItem(product, qty)
        } else {
            cart[product.id] = existing.copy(quantity = (existing.quantity + qty).coerceAtLeast(1))
        }
    }

    /** PUBLIC_INTERFACE
     * Update quantity; if <= 0 remove.
     */
    fun updateQuantity(productId: String, qty: Int) {
        val existing = cart[productId] ?: return
        if (qty <= 0) {
            cart.remove(productId)
        } else {
            cart[productId] = existing.copy(quantity = qty)
        }
    }

    /** PUBLIC_INTERFACE
     * Remove item from cart.
     */
    fun remove(productId: String) {
        cart.remove(productId)
    }

    /** PUBLIC_INTERFACE
     * Empty cart.
     */
    fun clear() = cart.clear()

    /** PUBLIC_INTERFACE
     * Cart total in cents.
     */
    fun totalCents(): Int = cart.values.sumOf { it.lineTotalCents }

    /** PUBLIC_INTERFACE
     * Checkout via API using provided auth token.
     */
    suspend fun checkout(token: String): OrderSummary {
        val payload = cart.mapValues { it.value.quantity }
        val summary = api.checkout(token, payload)
        clear()
        return summary
    }
}
