package com.playtogether.kmp.data.repositories

import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.Resource
import com.playtogether.kmp.data.util.ifStatusOk
import com.playtogether.kmp.data.util.safeApiCall
import com.russhwolf.settings.Settings
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
    suspend fun logout()
}

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val database: Deferred<PTDatabase>,
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
        response.ifStatusOk {
            val authToken = response.body<AuthResponse>().token
            settings.putString(Constants.SharedPrefKeys.AuthToken, authToken)
            database.await().pTDatabaseQueries.insertToken(authToken)
        }
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
        response.ifStatusOk {
            val authToken = response.body<AuthResponse>().token
            settings.putString(Constants.SharedPrefKeys.AuthToken, authToken)
            database.await().pTDatabaseQueries.insertToken(authToken)
        }
        response
    }

    override suspend fun isUserLoggedIn(): Flow<Boolean> {
        val db = database.await().pTDatabaseQueries
        if (db.fetchToken().executeAsOneOrNull() == null) {
            settings.getStringOrNull(Constants.SharedPrefKeys.AuthToken)?.let { db.insertToken(it) }
        }
        return db.fetchToken().asFlow().map {
            it.executeAsOneOrNull()?.token != null
        }
    }

    override suspend fun logout() {
        settings.remove(Constants.SharedPrefKeys.AuthToken)
        database.await().pTDatabaseQueries.deleteToken()
    }
}
