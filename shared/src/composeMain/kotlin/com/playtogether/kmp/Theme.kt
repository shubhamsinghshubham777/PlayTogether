package com.playtogether.kmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: Customise color scheme
private val PTDarkColorScheme @Composable get() = darkColorScheme()
private val PTLightColorScheme @Composable get() = lightColorScheme()

class Elevation internal constructor(
    val tiny: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
) {
    companion object {
        val None = 0.dp
    }
}

val LocalElevation = staticCompositionLocalOf {
    Elevation(
        tiny = 1.dp,
        small = 2.dp,
        medium = 4.dp,
        large = 8.dp,
        extraLarge = 16.dp,
    )
}

val MaterialTheme.elevation
    @Composable
    @ReadOnlyComposable
    get() = LocalElevation.current

val PTShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(32.dp)
)

val PTSheetShape @Composable get() = RoundedCornerShape(
    topStart = 32.dp,
    topEnd = 32.dp
)

class Spacing(
    val none: Dp,
    val tiny: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val huge: Dp,
)

val LocalSpacing = staticCompositionLocalOf {
    Spacing(
        none = 0.dp,
        tiny = 4.dp,
        small = 8.dp,
        medium = 12.dp,
        large = 16.dp,
        extraLarge = 24.dp,
        huge = 32.dp,
    )
}

val MaterialTheme.spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

private val playTogetherTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Composable
fun PTTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) PTDarkColorScheme else PTLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = playTogetherTypography,
        shapes = PTShapes,
        content = content
    )
}
