package com.playtogether.kmp.web.ui.screens.dashboard

import mui.material.Button
import mui.material.Typography
import mui.material.styles.TypographyVariant
import react.FC
import react.Props


external interface DashboardScreenProps : Props {
    var logout: () -> Unit
}

val DashboardScreen = FC<DashboardScreenProps> { props ->
    Typography {
        variant = TypographyVariant.h2
        +"Dashboard Screen"
    }
    Button {
        onClick = { props.logout() }
        +"Logout"
    }
}
