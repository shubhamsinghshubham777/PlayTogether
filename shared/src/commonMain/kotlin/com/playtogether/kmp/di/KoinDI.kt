package com.playtogether.kmp.di

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.repositories.AuthRepository
import com.playtogether.kmp.data.repositories.AuthRepositoryImpl
import com.playtogether.kmp.data.repositories.SettingsRepository
import com.playtogether.kmp.data.repositories.SettingsRepositoryImpl
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.presentation.viewmodels.AuthViewModel
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(builder: (KoinApplication.() -> Unit)? = null) = startKoin {
    builder?.invoke(this)
    modules(
        networkModule,
        repositoryModule,
        viewModelModule,
        dataSourceModule
    )
}

// ---------------------------------------------- MODULES ----------------------------------------------

private val networkModule = module {
    factory {
        val settings = get<Settings>()
        HttpClient {
            install(ContentNegotiation) { json() }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = settings.getString(Constants.SharedPrefKeys.AccessToken),
                            refreshToken = settings.getString(Constants.SharedPrefKeys.RefreshToken)
                        )
                    }
                    refreshTokens {
                        val response = client
                            .get(Constants.Server.Routes.updateAccessToken).body<AuthResponse>()

                        response.user.refreshToken?.let { nnRefreshToken ->
                            return@refreshTokens BearerTokens(
                                accessToken = response.accessToken,
                                refreshToken = nnRefreshToken
                            )
                        }
                    }
                }
            }
        }
    }
}

private val dataSourceModule = module {
    single { Settings() }
}

private val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(httpClient = get(), settings = get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(settings = get()) }
}

private val viewModelModule = module {
    single { MainViewModel(authRepository = get(), settingsRepository = get()) }
    single { AuthViewModel(authRepository = get()) }
}

// ---------------------------------------------- HELPERS ----------------------------------------------

object RepositoryDIHelper : KoinComponent {
    val authRepository = get<AuthRepository>()
    val settingsRepository = get<SettingsRepository>()
}

object ViewModelDIHelper : KoinComponent {
    val mainViewModel: MainViewModel = get()
    val authViewModel: AuthViewModel = get()
}
