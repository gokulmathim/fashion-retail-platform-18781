package org.example.app

import android.app.Application
import org.example.app.data.remote.ApiService
import org.example.app.data.remote.MockApiService
import org.example.app.data.repository.AuthRepository
import org.example.app.data.repository.CartRepository
import org.example.app.data.repository.ProductRepository

/**
 * App-level container for repositories and API services.
 */
class GarmentApp : Application() {

    lateinit var apiService: ApiService
        private set

    lateinit var productRepository: ProductRepository
        private set

    lateinit var authRepository: AuthRepository
        private set

    lateinit var cartRepository: CartRepository
        private set

    override fun onCreate() {
        super.onCreate()
        apiService = MockApiService()
        productRepository = ProductRepository(apiService)
        authRepository = AuthRepository(apiService)
        cartRepository = CartRepository(apiService)
    }
}
