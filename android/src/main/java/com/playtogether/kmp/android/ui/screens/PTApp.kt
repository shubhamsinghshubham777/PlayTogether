package com.playtogether.kmp.android.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.playtogether.kmp.android.ui.screens.auth.AuthScreen
import com.playtogether.kmp.android.ui.screens.dashboard.DashboardScreen
import com.playtogether.kmp.presentation.viewmodels.AuthViewModel
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import org.koin.androidx.compose.get

@Composable
fun PTApp() {
    val mainViewModel = get<MainViewModel>()
    val authViewModel = get<AuthViewModel>()

    val isUserLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
    val loginState by authViewModel.loginState.collectAsState()
    val registerState by authViewModel.registerState.collectAsState()

    Crossfade(targetState = isUserLoggedIn) { isLoggedIn ->
        if (isLoggedIn == true) {
            DashboardScreen()
        } else {
            AuthScreen(
                loginState = loginState,
                registerState = registerState,
                onLogin = authViewModel::login,
                onRegister = authViewModel::register
            )
        }
    }
}
