package com.playtogether.kmp.util

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier

actual fun Modifier.systemBarsPadding(): Modifier = then(systemBarsPadding())
actual fun Modifier.statusBarsPadding(): Modifier = then(statusBarsPadding())
actual fun Modifier.navigationBarsPadding(): Modifier = then(navigationBarsPadding())