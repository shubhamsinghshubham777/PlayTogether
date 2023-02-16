package com.playtogether.kmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.di.ViewModelDIHelper
import com.playtogether.kmp.screens.DashboardScreen
import com.playtogether.kmp.screens.auth.AuthScreen
import com.playtogether.kmp.util.LocalWindowSizeClass
import com.playtogether.kmp.util.WindowSizeClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PTApp(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    /**
     * Provides whether the darkMode value in DB is true.
     */
    darkMode: (Boolean) -> Unit = {}
) {
    val mainViewModel = remember { ViewModelDIHelper.mainViewModel }
    val authViewModel = remember { ViewModelDIHelper.authViewModel }
    val isUserLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
    val isDarkModeOn by mainViewModel.isDarkModeOn.collectAsState()

    LaunchedEffect(isDarkModeOn) { isDarkModeOn?.let(darkMode) }

    PTTheme(darkTheme = isDarkModeOn == true) {
        CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
            Surface(
                modifier = modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                isUserLoggedIn?.let { nnIsUserLoggedIn ->
                    val navigator = rememberNavigator()
                    val currentBackStack by navigator.currentEntry
                        .collectAsStateWithLifecycle(null)

                    LaunchedEffect(nnIsUserLoggedIn) {
                        if (
                            nnIsUserLoggedIn
                            && currentBackStack?.route?.route == Constants.NavRoutes.Auth
                        ) {
                            navigator.navigate(
                                route = Constants.NavRoutes.Root,
                                options = NavOptions(
                                    popUpTo = PopUpTo(Constants.NavRoutes.Auth, inclusive = true),
                                    launchSingleTop = true
                                )
                            )
                        }
                        if (
                            !nnIsUserLoggedIn
                            && currentBackStack?.route?.route != Constants.NavRoutes.Auth
                        ) {
                            navigator.navigate(
                                route = Constants.NavRoutes.Auth,
                                options = NavOptions(
                                    popUpTo = PopUpTo(Constants.NavRoutes.Root, inclusive = true),
                                    launchSingleTop = true
                                )
                            )
                        }
                    }

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
                                AuthScreen(
                                    onLogin = authViewModel::login,
                                    onRegister = authViewModel::register,
                                    loginState = authViewModel.loginState,
                                    registerState = authViewModel.registerState
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
