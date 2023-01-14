package com.playtogether.kmp.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Elevation(
    val Level1: Dp,
    val Level2: Dp,
    val Level3: Dp,
    val Level4: Dp,
    val Level5: Dp,
) {
    companion object {
        val None = 0.dp
    }
}

val LocalElevation = staticCompositionLocalOf {
    Elevation(
        Level1 = 1.dp,
        Level2 = 2.dp,
        Level3 = 4.dp,
        Level4 = 8.dp,
        Level5 = 16.dp,
    )
}

val MaterialTheme.elevation
    @Composable
    @ReadOnlyComposable
    get() = LocalElevation.current
