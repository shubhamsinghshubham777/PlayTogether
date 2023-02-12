package com.playtogether.kmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
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
private val PTDarkColorScheme @Composable get() = darkColors()
private val PTLightColorScheme @Composable get() = lightColors()

class Elevation internal constructor(
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

private val playTogetherTypography = Typography(
    body1 = TextStyle(
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
    val colors = if (darkTheme) PTDarkColorScheme else PTLightColorScheme

    MaterialTheme(
        colors = colors,
        typography = playTogetherTypography,
        shapes = PTShapes,
        content = content
    )
}
