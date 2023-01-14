package com.playtogether.kmp.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Spacing(
    val Level0: Dp,
    val Level1: Dp,
    val Level2: Dp,
    val Level3: Dp,
    val Level4: Dp,
    val Level5: Dp,
    val Level6: Dp,
)

val LocalSpacing = staticCompositionLocalOf {
    Spacing(
        Level0 = 0.dp,
        Level1 = 4.dp,
        Level2 = 8.dp,
        Level3 = 12.dp,
        Level4 = 16.dp,
        Level5 = 24.dp,
        Level6 = 32.dp,
    )
}

val MaterialTheme.spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
