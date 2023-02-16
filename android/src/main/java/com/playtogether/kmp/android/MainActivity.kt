package com.playtogether.kmp.android

import android.os.Bundle
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.playtogether.kmp.PTApp
import com.playtogether.kmp.util.ExperimentalMaterial3WindowSizeClassApi
import com.playtogether.kmp.util.WindowSizeClass
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val configuration = LocalConfiguration.current
            val systemUiController = rememberSystemUiController()
            var useDarkIcons by remember { mutableStateOf(false) }

            LaunchedEffect(systemUiController, useDarkIcons) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }

            PTApp(
                modifier = Modifier.imePadding(),
                windowSizeClass = WindowSizeClass.calculateFromSize(
                    size = DpSize(
                        width = configuration.screenWidthDp.dp,
                        height = configuration.screenHeightDp.dp
                    )
                ),
                darkMode = { isDarkModeOn -> useDarkIcons = !isDarkModeOn }
            )
        }
    }
}
