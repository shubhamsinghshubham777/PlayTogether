package com.playtogether.kmp.desktop

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.desktop.ui.screens.auth.AuthScreen
import com.playtogether.kmp.desktop.ui.screens.dashboard.DashboardScreen
import com.playtogether.kmp.di.ViewModelDIHelper
import com.playtogether.kmp.di.dataSourceModuleJVM
import com.playtogether.kmp.di.initKoin
import java.awt.Dimension

fun main() {
    initKoin {
        modules(dataSourceModuleJVM)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = Constants.Desktop.WindowTitle,
            state = rememberWindowState(
                width = 300.dp,
                height = 300.dp
            )
        ) {
            window.minimumSize = Dimension(800, 600)
            val mainViewModel = ViewModelDIHelper.mainViewModel
            val authViewModel = ViewModelDIHelper.authViewModel
            val isLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
            val loginState by authViewModel.loginState.collectAsState()
            MaterialTheme(colors = darkColors()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    isLoggedIn?.let { nnIsLoggedIn ->
                        if (nnIsLoggedIn) {
                            DashboardScreen(
                                onLogout = authViewModel::logout
                            )
                        } else {
                            AuthScreen(
                                loginState = loginState,
                                onLogin = authViewModel::login,
                                onRegister = authViewModel::register
                            )
                        }
                    }
                }
            }
        }
    }
}
