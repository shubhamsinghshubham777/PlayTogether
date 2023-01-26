package com.playtogether.kmp.di

import com.playtogether.kmp.data.sources.local.DatabaseDriverFactory
import org.koin.dsl.module

val dataSourceModuleIOS = module {
    single { DatabaseDriverFactory() }
}