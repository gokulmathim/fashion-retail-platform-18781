package org.example.app.data.remote

import org.example.app.data.model.OrderSummary
import org.example.app.data.model.Product
import org.example.app.data.model.UserSession

/**
 * PUBLIC_INTERFACE
 * API service interface to communicate with 'garment_store_database' backend.
 * This is a mock/stub implementation ready to be replaced by real HTTP calls.
 */
interface ApiService {
    /** PUBLIC_INTERFACE
     * Fetch products, optionally filtered by query text.
     */
    suspend fun getProducts(query: String? = null): List<Product>

    /** PUBLIC_INTERFACE
     * Attempt login and return a user session.
     */
    suspend fun login(email: String, password: String): UserSession

    /** PUBLIC_INTERFACE
     * Register a new user and return a session.
     */
    suspend fun register(name: String, email: String, password: String): UserSession

    /** PUBLIC_INTERFACE
     * Place an order and return an order summary.
     */
    suspend fun checkout(token: String, cart: Map<String, Int>): OrderSummary
}
