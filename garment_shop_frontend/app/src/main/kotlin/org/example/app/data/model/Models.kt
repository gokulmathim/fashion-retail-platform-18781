package org.example.app.data.model

import java.util.UUID

/**
 * PUBLIC_INTERFACE
 * Represents a garment product.
 */
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val priceCents: Int,
    val imageUrl: String? = null,
    val category: String = "General",
)

/**
 * PUBLIC_INTERFACE
 * Represents an item in the cart.
 */
data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val lineTotalCents: Int get() = product.priceCents * quantity
}

/**
 * PUBLIC_INTERFACE
 * Represents an authenticated user session.
 */
data class UserSession(
    val userId: String,
    val email: String,
    val token: String
)

/**
 * PUBLIC_INTERFACE
 * Represents a simple order summary.
 */
data class OrderSummary(
    val orderId: String,
    val totalCents: Int,
    val itemCount: Int
)
