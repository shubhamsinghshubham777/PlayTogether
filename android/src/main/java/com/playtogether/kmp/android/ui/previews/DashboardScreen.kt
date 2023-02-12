package com.playtogether.kmp.android.ui.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.playtogether.kmp.PTTheme
import com.playtogether.kmp.screens.DashboardScreen

@DevicePreviews
@Composable
private fun DashboardScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            DashboardScreen(
                toggleTheme = {},
                logout = {}
            )
        }
    }
}