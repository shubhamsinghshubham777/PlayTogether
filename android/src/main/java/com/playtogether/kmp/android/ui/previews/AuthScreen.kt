package com.playtogether.kmp.android.ui.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.playtogether.kmp.PTTheme
import com.playtogether.kmp.presentation.util.UIState
import com.playtogether.kmp.screens.auth.AuthScreen
import com.playtogether.kmp.util.ExperimentalMaterial3WindowSizeClassApi
import com.playtogether.kmp.util.LocalWindowSizeClass
import kotlinx.coroutines.flow.flowOf

/**
 * Preview for [AuthScreen]
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@DevicePreviews
@Composable
private fun AuthScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(
                LocalWindowSizeClass provides rememberWindowSizeClass()
            ) {
                AuthScreen(
                    onLogin = { _, _ -> },
                    onRegister = { _, _ -> },
                    loginState = flowOf(UIState.Failure(Exception("Test error..."))),
                    registerState = flowOf(UIState.Empty),
                )
            }
        }
    }
}
