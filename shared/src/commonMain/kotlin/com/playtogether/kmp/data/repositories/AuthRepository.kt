package com.playtogether.kmp.data.repositories

import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.Resource
import com.playtogether.kmp.data.util.safeApiCall
import com.squareup.sqldelight.runtime.coroutines.asFlow
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<AuthResponse>
    suspend fun register(email: String, password: String): Resource<AuthResponse>
    suspend fun isUserLoggedIn(): Flow<Boolean>
}

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val database: Deferred<PTDatabase>
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Resource<AuthResponse> = safeApiCall {
        val response = httpClient.post(Constants.NetworkClientEndpoints.Login) {
            parameter(key = Constants.Server.Params.UserEmail, value = email)
            parameter(key = Constants.Server.Params.UserPassword, value = password)
        }
        database.await().pTDatabaseQueries.insertToken(response.body<AuthResponse>().token)
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
        database.await().pTDatabaseQueries.insertToken(response.body<AuthResponse>().token)
        response
    }

    override suspend fun isUserLoggedIn(): Flow<Boolean> {
        return database.await().pTDatabaseQueries.fetchToken().asFlow()
            .map { !it.executeAsOneOrNull()?.token.isNullOrBlank() }
    }
}
