package com.playtogether.kmp.di

import com.playtogether.kmp.data.repositories.AuthRepository
import com.playtogether.kmp.data.repositories.AuthRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(httpClient = get(), settings = get()) }
}

object RepositoryDIHelper : KoinComponent {
    val authRepository = get<AuthRepository>()
}
