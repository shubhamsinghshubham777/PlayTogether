package com.playtogether.kmp.di

import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.repositories.AuthRepository
import com.playtogether.kmp.data.repositories.AuthRepositoryImpl
import com.playtogether.kmp.data.sources.local.DatabaseDriverFactory
import com.playtogether.kmp.presentation.viewmodels.AuthViewModel
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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
    single { HttpClient() }
}

@OptIn(DelicateCoroutinesApi::class)
private val dataSourceModule = module {
    single { GlobalScope.async { PTDatabase(driver = get<DatabaseDriverFactory>().createDriver()) } }
}

private val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(httpClient = get(), database = get()) }
}

private val viewModelModule = module {
    single { MainViewModel(authRepository = get()) }
    single { AuthViewModel(authRepository = get()) }
}

// ---------------------------------------------- HELPERS ----------------------------------------------

object RepositoryDIHelper : KoinComponent {
    val authRepository = get<AuthRepository>()
}

object ViewModelDIHelper : KoinComponent {
    val mainViewModel: MainViewModel = get()
    val authViewModel: AuthViewModel = get()
}
