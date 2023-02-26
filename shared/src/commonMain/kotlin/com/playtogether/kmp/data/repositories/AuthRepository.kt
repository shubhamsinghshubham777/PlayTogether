package com.playtogether.kmp.data.repositories

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.Resource
import com.playtogether.kmp.data.util.ifStatusOk
import com.playtogether.kmp.data.util.safeApiCall
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<AuthResponse>
    suspend fun register(email: String, password: String): Resource<AuthResponse>
    suspend fun isUserLoggedIn(): Flow<Boolean>
    suspend fun logout()
}

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val settings: Settings
) : AuthRepository {
    private val isUserLoggedInState: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    init {
        isUserLoggedInState.update {
            settings.getStringOrNull(Constants.SharedPrefKeys.AuthToken) != null
        }
    }

    private suspend inline fun refreshIsLoggedInState(executeFirst: () -> Unit) {
        executeFirst()
        isUserLoggedInState.emit(
            settings.getStringOrNull(Constants.SharedPrefKeys.AuthToken) != null
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): Resource<AuthResponse> = safeApiCall {
        val response = httpClient.post(Constants.NetworkClientEndpoints.Login) {
            parameter(key = Constants.Server.Params.UserEmail, value = email)
            parameter(key = Constants.Server.Params.UserPassword, value = password)
        }
        response.ifStatusOk {
            refreshIsLoggedInState(
                executeFirst = {
                    val authToken = response.body<AuthResponse>().accessToken
                    settings.putString(Constants.SharedPrefKeys.AuthToken, authToken)
                }
            )
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
            refreshIsLoggedInState(
                executeFirst = {
                    val authToken = response.body<AuthResponse>().accessToken
                    settings.putString(Constants.SharedPrefKeys.AuthToken, authToken)
                }
            )
        }
        response
    }

    override suspend fun isUserLoggedIn(): Flow<Boolean> {
        return isUserLoggedInState.filterNotNull()
    }

    override suspend fun logout() {
        refreshIsLoggedInState(
            executeFirst = {
                settings.remove(Constants.SharedPrefKeys.AuthToken)
            }
        )
    }
}
