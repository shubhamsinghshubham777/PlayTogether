package com.playtogether.kmp.data.repositories

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.Resource
import com.playtogether.kmp.data.util.safeApiCall
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<AuthResponse>
    suspend fun register(email: String, password: String): Resource<AuthResponse>
    suspend fun isUserLoggedIn(): Boolean
}

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val settings: Settings
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Resource<AuthResponse> = safeApiCall {
        val response = httpClient.post(Constants.NetworkClientEndpoints.Login) {
            parameter(key = Constants.Server.Params.UserEmail, value = email)
            parameter(key = Constants.Server.Params.UserPassword, value = password)
        }
        settings.putString(key = Constants.SharedPrefs.AuthToken, value = response.body<AuthResponse>().token)
        response
    }

    override suspend fun register(
        email: String,
        password: String
    ): Resource<AuthResponse> = safeApiCall {
        val response = httpClient.post(Constants.NetworkClientEndpoints.Register) {
            parameter(Constants.Server.Params.UserEmail, email)
            parameter(Constants.Server.Params.UserPassword, password)
        }
        settings.putString(key = Constants.SharedPrefs.AuthToken, value = response.body<AuthResponse>().token)
        response
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return settings.getString(key = Constants.SharedPrefs.AuthToken).isNotBlank()
    }
}
