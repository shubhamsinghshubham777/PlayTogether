package com.playtogether.kmp.screens.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.elevation
import com.playtogether.kmp.spacing
import com.playtogether.kmp.util.rememberResponsiveWidthValue

@Composable
fun GreetingSection() {
    var textBlurRadius by remember { mutableStateOf(0f) }
    val animatedTextBlurRadius by animateFloatAsState(
        targetValue = textBlurRadius,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        textBlurRadius = 2f
    }

    Text(
        modifier = Modifier.padding(top = MaterialTheme.spacing.huge),
        text = Constants.App.Name,
        style = MaterialTheme.typography.displayLarge.copy(
            shadow = Shadow(
                color = MaterialTheme.colorScheme.tertiary,
                blurRadius = with(LocalDensity.current) {
                    MaterialTheme.elevation.extraLarge.toPx().times(animatedTextBlurRadius)
                }
            )
        )
    )
    Text(
        modifier = Modifier
            .fillMaxWidth(
                rememberResponsiveWidthValue(
                    compact = 0.7f,
                    expanded = 0.75f
                )
            )
            .padding(vertical = MaterialTheme.spacing.huge)
            .graphicsLayer { alpha = 0.5f },
        text = Constants.App.ShortDescription,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
    Text(
        text = Constants.Auth.Title,
        style = MaterialTheme.typography.displaySmall.copy(
            shadow = Shadow(
                color = MaterialTheme.colorScheme.tertiary,
                blurRadius = with(LocalDensity.current) {
                    MaterialTheme.elevation.extraLarge.toPx().times(animatedTextBlurRadius)
                }
            )
        )
    )
}
