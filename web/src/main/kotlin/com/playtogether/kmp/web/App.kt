package com.playtogether.kmp.web

import com.playtogether.kmp.di.initKoin
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.ToastifyModule
import io.kvision.module
import io.kvision.panel.root
import io.kvision.react.react
import io.kvision.startApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mui.material.Typography
import org.koin.core.component.KoinComponent

class App : Application(), CoroutineScope by CoroutineScope(Dispatchers.Main), KoinComponent {
    @Suppress("UnsafeCastFromDynamic")
    override fun start() {
        root("kvapp") {
            react {
                Typography {
                    +"Hello, world!"
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
    initKoin()
}
