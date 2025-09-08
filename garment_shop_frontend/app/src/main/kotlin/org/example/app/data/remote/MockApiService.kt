package org.example.app.data.remote

import kotlinx.coroutines.delay
import org.example.app.data.model.OrderSummary
import org.example.app.data.model.Product
import org.example.app.data.model.UserSession
import java.util.UUID
import kotlin.math.absoluteValue

/**
 * Mock implementation of ApiService that simulates network calls with delay.
 * Replace with a real Retrofit/Moshi implementation for production.
 */
class MockApiService : ApiService {

    private val catalog = listOf(
        Product(name = "Classic Tee", description = "100% cotton tee.", priceCents = 1999, category = "Tops"),
        Product(name = "Slim Jeans", description = "Denim slim fit.", priceCents = 4599, category = "Bottoms"),
        Product(name = "Hoodie", description = "Cozy fleece hoodie.", priceCents = 3899, category = "Outerwear"),
        Product(name = "Summer Dress", description = "Light & breezy.", priceCents = 5599, category = "Dresses"),
        Product(name = "Chino Shorts", description = "Casual shorts.", priceCents = 2999, category = "Bottoms"),
        Product(name = "Sneakers", description = "All-day comfort.", priceCents = 6499, category = "Footwear"),
    )

    override suspend fun getProducts(query: String?): List<Product> {
        delay(250) // Simulate latency
        val q = query?.trim()?.lowercase().orEmpty()
        return if (q.isBlank()) catalog else catalog.filter {
            it.name.lowercase().contains(q) || it.category.lowercase().contains(q)
        }
    }

    override suspend fun login(email: String, password: String): UserSession {
        delay(250)
        // Simple fake validation
        return UserSession(
            userId = UUID.nameUUIDFromBytes(email.toByteArray()).toString(),
            email = email,
            token = UUID.randomUUID().toString()
        )
    }

    override suspend fun register(name: String, email: String, password: String): UserSession {
        delay(350)
        return UserSession(
            userId = UUID.nameUUIDFromBytes("$name|$email".toByteArray()).toString(),
            email = email,
            token = UUID.randomUUID().toString()
        )
    }

    override suspend fun checkout(token: String, cart: Map<String, Int>): OrderSummary {
        delay(500)
        val itemCount = cart.values.sum()
        val total = cart.entries.sumOf { (id, qty) ->
            val product = catalog.firstOrNull { it.id == id }
            (product?.priceCents ?: 0) * qty
        }
        return OrderSummary(
            orderId = UUID.randomUUID().toString(),
            totalCents = total.absoluteValue,
            itemCount = itemCount
        )
    }
}
