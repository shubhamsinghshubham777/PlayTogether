package com.playtogether.kmp.android.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.playtogether.kmp.util.ExperimentalMaterial3WindowSizeClassApi
import com.playtogether.kmp.util.WindowSizeClass

@ExperimentalMaterial3WindowSizeClassApi
@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val config = LocalConfiguration.current
    return remember {
        WindowSizeClass.calculateFromSize(
            size = DpSize(width = config.screenWidthDp.dp, height = config.screenHeightDp.dp)
        )
    }
}
