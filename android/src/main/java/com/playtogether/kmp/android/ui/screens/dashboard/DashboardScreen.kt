package com.playtogether.kmp.android.ui.screens.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.playtogether.kmp.android.ui.theme.PTTheme
import com.playtogether.kmp.android.ui.util.DevicePreviews

@Composable
fun DashboardScreen() {
    Text(text = "Dashboard Screen")
}

@DevicePreviews
@Composable
private fun DashboardScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            DashboardScreen()
        }
    }
}