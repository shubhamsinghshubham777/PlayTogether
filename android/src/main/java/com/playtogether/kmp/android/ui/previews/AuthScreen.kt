package com.playtogether.kmp.android.ui.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.playtogether.kmp.PTTheme
import com.playtogether.kmp.screens.AuthScreen

@DevicePreviews
@Composable
private fun AuthScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AuthScreen()
        }
    }
}
