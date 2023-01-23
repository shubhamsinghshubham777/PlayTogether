package com.playtogether.kmp.di

import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val dataSourcesModule = module {
    single { Settings() }
}

object DataSourcesHelper : KoinComponent {
    val settings = get<Settings>()
}
