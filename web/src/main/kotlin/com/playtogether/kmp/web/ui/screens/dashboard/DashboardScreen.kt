package com.playtogether.kmp.web.ui.screens.dashboard

import mui.material.Typography
import mui.material.styles.TypographyVariant
import react.FC
import react.Props


external interface DashboardScreenProps : Props {
}

val DashboardScreen = FC<DashboardScreenProps> { props ->
    Typography {
        variant = TypographyVariant.h2
        +"Dashboard Screen"
    }
}
