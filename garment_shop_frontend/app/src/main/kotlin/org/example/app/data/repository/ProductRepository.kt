package org.example.app.data.repository

import org.example.app.data.model.Product
import org.example.app.data.remote.ApiService

/**
 * PUBLIC_INTERFACE
 * Repository for products. Abstracts the data source from UI.
 */
class ProductRepository(
    private val api: ApiService
) {
    /** PUBLIC_INTERFACE
     * Fetch products filtered by optional query.
     */
    suspend fun getProducts(query: String?): List<Product> = api.getProducts(query)
}
