package com.playtogether.kmp.web.ui.screens.auth

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.types.AuthType
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.presentation.util.UIState
import com.playtogether.kmp.web.util.currentValue
import csstype.BackgroundRepeat
import csstype.GeometryPosition
import csstype.blur
import csstype.brightness
import csstype.dropShadow
import csstype.px
import csstype.url
import csstype.vh
import dom.html.HTMLInputElement
import kotlinx.coroutines.flow.StateFlow
import mui.material.Box
import mui.material.Button
import mui.material.ButtonColor
import mui.material.ButtonVariant
import mui.material.FormControlVariant
import mui.material.Stack
import mui.material.StackDirection
import mui.material.TextField
import mui.material.Typography
import mui.material.styles.Theme
import mui.material.styles.TypographyVariant
import mui.material.styles.useTheme
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.create
import react.dom.onChange
import react.useState

external interface AuthScreenProps : Props {
    var login: (email: String, password: String) -> Unit
    var loginState: StateFlow<UIState<AuthResponse>>
    var register: (email: String, password: String) -> Unit
    var registerState: StateFlow<UIState<AuthResponse>>
}

val AuthScreen = FC<AuthScreenProps> {
    val theme = useTheme<Theme>()
    var email by useState("")
    var password by useState("")
    var repeatPassword by useState("")
    var authType by useState(AuthType.Login)

    Box {
        sx {
            backgroundImage = url("app_logo.svg")
            backgroundRepeat = BackgroundRepeat.noRepeat
            backgroundSize = 400.px
            backgroundPosition = GeometryPosition.center
        }
        Box {
            sx {
                backdropFilter = blur(4.px)
            }
            Stack {
                sx {
                    minHeight = 100.vh
                    backdropFilter = brightness(0.5)
                }
                direction = responsive(StackDirection.column)
                spacing = responsive(2)
                Typography {
                    sx {
                        filter = dropShadow(offsetX = 2.px, offsetY = 2.px, blurRadius = 4.px)
                    }
                    variant = TypographyVariant.h2
                    +"PlayTogether"
                }
                TextField {
                    this.onChange = {
                        email = it.currentValue
                    }
                    this.variant = FormControlVariant.outlined
                    this.label = Typography.create {
                        +Constants.Auth.EmailLabel
                    }
                }
                TextField {
                    this.onChange = {
                        password = it.currentValue
                    }
                    this.variant = FormControlVariant.outlined
                    this.label = Typography.create {
                        +Constants.Auth.PasswordLabel
                    }
                }
                TextField {
                    this.onChange = {
                        console.log((it.target as HTMLInputElement).value)
                        repeatPassword = it.currentValue
                    }
                    this.variant = FormControlVariant.outlined
                    this.label = Typography.create {
                        +Constants.Auth.RepeatPasswordLabel
                    }
                }
                Button {
                    color = ButtonColor.primary
                    variant = ButtonVariant.contained
                    +authType.name
                }
            }
        }
    }
}
