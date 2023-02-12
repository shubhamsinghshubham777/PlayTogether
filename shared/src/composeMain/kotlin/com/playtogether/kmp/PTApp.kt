package com.playtogether.kmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.di.ViewModelDIHelper
import com.playtogether.kmp.screens.AuthScreen
import com.playtogether.kmp.screens.DashboardScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun PTApp() {
    val mainViewModel = remember { ViewModelDIHelper.mainViewModel }
    val authViewModel = remember { ViewModelDIHelper.authViewModel }
    val isUserLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
    val isDarkModeOn by mainViewModel.isDarkModeOn.collectAsState()
    PTTheme(darkTheme = isDarkModeOn == true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            isUserLoggedIn?.let { nnIsUserLoggedIn ->
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    initialRoute = if (nnIsUserLoggedIn) Constants.NavRoutes.Root
                    else Constants.NavRoutes.Auth
                ) {
                    with(Constants.NavRoutes) {
                        scene(Root) {
                            DashboardScreen(
                                toggleTheme = {
                                    isDarkModeOn?.let { mainViewModel.setIsDarkThemeOn(!it) }
                                },
                                logout = authViewModel::logout
                            )
                        }
                        scene(Auth) {
                            AuthScreen()
                        }
                    }
                }
            }
        }
    }
}