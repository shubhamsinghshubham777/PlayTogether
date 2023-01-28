package com.playtogether.kmp.desktop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val ptDarkColors = darkColors()
private val ptLightColors = lightColors()
private val ptTypography = Typography()
private val ptShapes = Shapes()

@Composable
fun PTTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) ptDarkColors else ptLightColors,
        typography = ptTypography,
        shapes = ptShapes,
        content = content
    )
}
