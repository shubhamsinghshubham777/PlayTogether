package com.playtogether.kmp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <T> rememberResponsiveWidthValue(
    compact: T,
    medium: T = compact,
    expanded: T = medium
): T {
    val windowWidth = LocalWindowSizeClass.current.widthSizeClass
    return remember(windowWidth) {
        when (windowWidth) {
            WindowWidthSizeClass.Compact -> compact
            WindowWidthSizeClass.Medium -> medium
            WindowWidthSizeClass.Expanded -> expanded
            else -> throw Exception("Unknown width!")
        }
    }
}

@Composable
fun <T> rememberResponsiveHeightValue(
    compact: T,
    medium: T = compact,
    expanded: T = medium
): T {
    val windowHeight = LocalWindowSizeClass.current.heightSizeClass
    return remember(windowHeight) {
        when (windowHeight) {
            WindowHeightSizeClass.Compact -> compact
            WindowHeightSizeClass.Medium -> medium
            WindowHeightSizeClass.Expanded -> expanded
            else -> throw Exception("Unknown height!")
        }
    }
}

expect fun Modifier.systemBarsPadding(): Modifier
expect fun Modifier.statusBarsPadding(): Modifier
expect fun Modifier.navigationBarsPadding(): Modifier
