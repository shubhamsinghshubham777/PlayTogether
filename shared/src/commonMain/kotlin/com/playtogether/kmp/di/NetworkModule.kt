package com.playtogether.kmp.di

import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val networkModule = module {
    single { HttpClient() }
}

object NetworkDIHelper : KoinComponent {
    val httpClient = get<HttpClient>()
}
