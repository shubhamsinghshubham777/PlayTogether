package com.playtogether.kmp.android.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val PTShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(32.dp)
)

val PTSheetShape @Composable get() = RoundedCornerShape(
    topStart = 32.dp,
    topEnd = 32.dp
)
