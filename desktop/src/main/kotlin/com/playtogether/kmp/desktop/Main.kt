package com.playtogether.kmp.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.playtogether.kmp.PTApp
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.di.dataSourceModuleJVM
import com.playtogether.kmp.di.initKoin
import moe.tlaster.precompose.PreComposeWindow
import java.awt.Dimension

fun main() {
    initKoin {
        modules(dataSourceModuleJVM)
    }
    application {
        PreComposeWindow(
            onCloseRequest = ::exitApplication,
            title = Constants.Desktop.WindowTitle,
            state = rememberWindowState(
                width = 300.dp,
                height = 300.dp
            )
        ) {
            window.minimumSize = Dimension(800, 600)
            PTApp()
        }
    }
}
