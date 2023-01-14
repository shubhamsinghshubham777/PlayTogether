package com.playtogether.kmp.android.ui.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

private const val DAY_BG_COLOR = 0xFFFFFFFF
private const val NIGHT_BG_COLOR = 0xFF3C4042

@Preview(
    name = "1. Phone Night",
    device = Devices.PHONE,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = NIGHT_BG_COLOR
)
@Preview(
    name = "2. Phone Day",
    device = Devices.PHONE,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = DAY_BG_COLOR
)
@Preview(
    name = "3. Foldable Night",
    device = Devices.FOLDABLE,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = NIGHT_BG_COLOR
)
@Preview(
    name = "4. Foldable Day",
    device = Devices.FOLDABLE,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = DAY_BG_COLOR
)
@Preview(
    name = "5. Tablet Night",
    device = Devices.TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = NIGHT_BG_COLOR
)
@Preview(
    name = "6. Tablet Day",
    device = Devices.TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = DAY_BG_COLOR
)
annotation class DevicePreviews
