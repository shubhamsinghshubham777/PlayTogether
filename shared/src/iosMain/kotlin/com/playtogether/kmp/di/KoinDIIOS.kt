package com.playtogether.kmp.di

fun initKoinIOS() = initKoin {
    modules(dataSourceModuleIOS)
}
