package com.playtogether.kmp.desktop

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.playtogether.kmp.di.ViewModelDIHelper
import com.playtogether.kmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Compose for Desktop",
            state = rememberWindowState(
                width = 300.dp,
                height = 300.dp
            )
        ) {
            val mainViewModel = ViewModelDIHelper.mainViewModel
            val isLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
            MaterialTheme(colors = darkColors()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("Hello, Desktop! Are you logged in?: $isLoggedIn")
                }
            }
        }
    }
}
