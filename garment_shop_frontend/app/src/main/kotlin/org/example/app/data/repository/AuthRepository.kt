package org.example.app.data.repository

import org.example.app.data.model.UserSession
import org.example.app.data.remote.ApiService

/**
 * PUBLIC_INTERFACE
 * Repository for authentication.
 */
class AuthRepository(
    private val api: ApiService
) {
    /** PUBLIC_INTERFACE
     * Login with email and password.
     */
    suspend fun login(email: String, password: String): UserSession = api.login(email, password)

    /** PUBLIC_INTERFACE
     * Register a user.
     */
    suspend fun register(name: String, email: String, password: String): UserSession = api.register(name, email, password)
}
