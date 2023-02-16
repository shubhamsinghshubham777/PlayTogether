package com.playtogether.kmp.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.playtogether.kmp.PTApp
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.di.dataSourceModuleJVM
import com.playtogether.kmp.di.initKoin
import com.playtogether.kmp.util.ExperimentalMaterial3WindowSizeClassApi
import com.playtogether.kmp.util.WindowSizeClass
import moe.tlaster.precompose.PreComposeWindow
import java.awt.Dimension

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() {
    initKoin {
        modules(dataSourceModuleJVM)
    }
    application {
        val windowState = rememberWindowState(
            width = 300.dp,
            height = 300.dp
        )
        PreComposeWindow(
            onCloseRequest = ::exitApplication,
            title = Constants.Desktop.WindowTitle,
            state = windowState
        ) {
            window.minimumSize = Dimension(800, 600)
            PTApp(
                windowSizeClass = WindowSizeClass.calculateFromSize(
                    size = windowState.size
                )
            )
        }
    }
}
