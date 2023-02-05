package com.playtogether.kmp.web.ui

import js.core.jso
import mui.material.PaletteMode
import mui.material.styles.createTheme

// TODO: Create an app-specific theme
val PTTheme = createTheme(
    jso {
        palette = jso { mode = PaletteMode.dark }
    }
)
