package com.playtogether.kmp.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PTTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) PTDarkColorScheme else PTLightColorScheme

    MaterialTheme(
        colors = colors,
        typography = PTTypography,
        shapes = PTShapes,
        content = content
    )
}
