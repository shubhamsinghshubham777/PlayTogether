package com.playtogether.kmp.web

import com.playtogether.kmp.di.initKoin
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.ToastifyModule
import io.kvision.module
import io.kvision.panel.root
import io.kvision.react.kv
import io.kvision.react.react
import io.kvision.startApplication
import io.kvision.state.bind
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mui.material.Typography
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.useState
import com.playtogether.kmp.di.dataSourceModuleJS

class App : Application(), CoroutineScope by CoroutineScope(Dispatchers.Main), KoinComponent {
    private val mainViewModel = get<MainViewModel>()
    @Suppress("UnsafeCastFromDynamic")
    override fun start() {
        root("kvapp") {
            react {
                var isUserLoggedIn by useState(false)
                kv {
                    bind(mainViewModel.isUserLoggedIn) { isUserLoggedIn = it }
                }
                Typography {
                    +"Hello, world! Are you logged in? $isUserLoggedIn"
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        ToastifyModule,
        CoreModule
    )
    initKoin {
        modules(dataSourceModuleJS)
    }
}
