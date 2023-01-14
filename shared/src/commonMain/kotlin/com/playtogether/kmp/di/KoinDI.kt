package com.playtogether.kmp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(builder: (KoinApplication.() -> Unit)? = null) = startKoin {
    builder?.invoke(this)
    modules(
        networkModule,
        repositoryModule,
        useCaseModule,
        viewModelModule
    )
}
