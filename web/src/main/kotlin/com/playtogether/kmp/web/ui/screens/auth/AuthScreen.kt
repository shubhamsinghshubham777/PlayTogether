package com.playtogether.kmp.web.ui.screens.auth

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.types.AuthType
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.RegexPatterns
import com.playtogether.kmp.presentation.util.UIState
import com.playtogether.kmp.web.AuthContext
import com.playtogether.kmp.web.util.GridPropsExtensions.md
import com.playtogether.kmp.web.util.GridPropsExtensions.xs
import com.playtogether.kmp.web.util.NavigateOptions
import csstype.AlignItems
import csstype.BackgroundRepeat
import csstype.GeometryPosition
import csstype.JustifyContent
import csstype.Length
import csstype.Padding
import csstype.PropertiesBuilder
import csstype.TextAlign
import csstype.dropShadow
import csstype.pct
import csstype.px
import csstype.url
import csstype.vh
import io.kvision.react.kv
import io.kvision.state.bind
import kotlinx.coroutines.flow.StateFlow
import mui.material.Box
import mui.material.Button
import mui.material.ButtonVariant
import mui.material.CircularProgress
import mui.material.Grid
import mui.material.GridDirection
import mui.material.Stack
import mui.material.StackDirection
import mui.material.Typography
import mui.material.styles.Theme
import mui.material.styles.TypographyVariant
import mui.material.styles.useTheme
import mui.material.useMediaQuery
import mui.system.Breakpoint
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.VFC
import react.router.useLocation
import react.router.useNavigate
import react.useContext
import react.useEffect
import react.useState
import web.url.URLSearchParams

external interface AuthScreenProps : Props {
    var login: (email: String, password: String) -> Unit
    var loginState: StateFlow<UIState<AuthResponse>>
    var register: (email: String, password: String) -> Unit
    var registerState: StateFlow<UIState<AuthResponse>>
}

val AuthScreen = FC<AuthScreenProps> { props ->
    val theme = useTheme<Theme>()
    val isScreenSmall = useMediaQuery(theme.breakpoints.down(Breakpoint.md))
    val navigate = useNavigate()
    val location = useLocation()

    var email by useState("")
    var isEmailValid by useState(true)

    var password by useState("")
    var isPasswordSecure by useState(true)
    var isPasswordValid by useState(true)

    var repeatPassword by useState("")
    var isRepeatPasswordSecure by useState(true)
    var isRepeatPasswordValid by useState(true)

    var areCredentialsValid by useState(true)

    val isUserLoggedIn = useContext(AuthContext) == true
    var authType by useState(AuthType.Login)

    var loginState: UIState<AuthResponse>? by useState(null)
    var registerState: UIState<AuthResponse>? by useState(null)

    kv {
        bind(props.loginState) { loginState = it }
        bind(props.registerState) { registerState = it }
    }

    useEffect(email) { isEmailValid = RegexPatterns.Email.matches(email) }

    useEffect(password, repeatPassword) {
        isPasswordValid = RegexPatterns.Password.matches(password)
        isRepeatPasswordValid = repeatPassword == password
    }

    useEffect(isEmailValid, isPasswordValid, isRepeatPasswordValid, authType) {
        areCredentialsValid = when (authType) {
            AuthType.Register -> isEmailValid && isPasswordValid && isRepeatPasswordValid
            AuthType.Login -> isEmailValid && isPasswordValid
        }
    }

    useEffect(isUserLoggedIn) {
        URLSearchParams(init = location.search)[Constants.NavRoutes.Params.continueRoute]?.let { continuePath ->
            if (isUserLoggedIn) navigate(to = continuePath, options = NavigateOptions(replace = true))
        }
    }

    Grid {
        container = true
        direction = responsive(GridDirection.row)
        sx {
            alignItems = AlignItems.center
        }

        if (!isScreenSmall) LargeScreenBackground {}

        Grid {
            item = true
            xs = 12
            md = 6
            Stack {
                sx {
                    padding = theme.spacing(4)
                    alignItems = AlignItems.center
                }
                direction = responsive(StackDirection.column)

                if (isScreenSmall) {
                    Box {
                        sx {
                            val logoSizePx = 150.px
                            width = logoSizePx
                            height = logoSizePx
                            backgroundLogoProps(logoSizePx)
                            marginTop = 16.px
                        }
                    }
                }

                Typography {
                    sx {
                        color = theme.palette.primary.main
                        filter = textDropShadow
                    }
                    variant = TypographyVariant.h2
                    +Constants.App.Name
                }

                Typography {
                    sx {
                        primaryContentSizeProps()
                        textAlign = TextAlign.center
                    }
                    variant = TypographyVariant.body2
                    +Constants.App.ShortDescription
                }

                Typography {
                    sx {
                        paddingTop = 32.px
                        textAlign = TextAlign.center
                        filter = textDropShadow
                    }
                    variant = TypographyVariant.h4
                    +Constants.Auth.Title
                }

                TextFieldSection {
                    this.email = email
                    this.onEmailChange = { email = it }
                    this.isEmailValid = isEmailValid

                    this.password = password
                    this.onPasswordChange = { password = it }
                    this.isPasswordValid = isPasswordValid

                    this.isPasswordSecure = isPasswordSecure
                    this.onPasswordEndAdornmentClick = { isPasswordSecure = !isPasswordSecure }

                    this.repeatPassword = repeatPassword
                    this.onRepeatPasswordChange = { repeatPassword = it }
                    this.isRepeatPasswordValid = isRepeatPasswordValid

                    this.isRepeatPasswordSecure = isRepeatPasswordSecure
                    this.onRepeatPasswordEndAdornmentClick = { isRepeatPasswordSecure = !isRepeatPasswordSecure }

                    this.authType = authType
                }

                val errorMessage = when (authType) {
                    AuthType.Register -> {
                        val failureMessage = (registerState as? UIState.Failure)?.exception?.message.orEmpty()
                        failureMessage.ifBlank { null }
                    }

                    AuthType.Login -> {
                        val failureMessage = (loginState as? UIState.Failure)?.exception?.message.orEmpty()
                        failureMessage.ifBlank { null }
                    }
                }
                errorMessage?.let { nnErrorMessage ->
                    Typography {
                        sx {
                            padding = Padding(vertical = 16.px, horizontal = 0.px)
                            color = theme.palette.error.main
                        }
                        variant = TypographyVariant.caption
                        +nnErrorMessage
                    }
                }

                Stack {
                    direction = responsive(StackDirection.row)
                    sx {
                        justifyContent = JustifyContent.spaceBetween
                        alignItems = AlignItems.center
                        primaryContentSizeProps()
                    }

                    Button {
                        variant = ButtonVariant.contained
                        onClick = {
                            when (authType) {
                                AuthType.Register -> props.register(email, password)
                                AuthType.Login -> props.login(email, password)
                            }
                        }
                        val isLoading = registerState is UIState.Loading || loginState is UIState.Loading
                        disabled = isLoading.or(!areCredentialsValid)
                        if (isLoading) CircularProgress() else +authType.name
                    }

                    Button {
                        sx { textAlign = TextAlign.end }
                        variant = ButtonVariant.text
                        onClick = {
                            authType = when (authType) {
                                AuthType.Register -> AuthType.Login
                                AuthType.Login -> AuthType.Register
                            }
                        }
                        disabled = (registerState is UIState.Loading).or(loginState is UIState.Loading)

                        +when (authType) {
                            AuthType.Register -> Constants.Auth.RegisterAuthTypeMessage
                            AuthType.Login -> Constants.Auth.LoginAuthTypeMessage
                        }
                    }
                }
            }
        }
    }
}

val LargeScreenBackground = VFC {
    val theme = useTheme<Theme>()
    Grid {
        item = true
        xs = 0
        md = 6
        Box {
            sx {
                backgroundLogoProps()
                minHeight = 100.vh
                backgroundColor = theme.palette.primary.main
                borderTopRightRadius = theme.spacing(4)
                borderBottomRightRadius = borderTopRightRadius
            }
        }
    }
}


private fun PropertiesBuilder.backgroundLogoProps(size: Length = 400.px) {
    backgroundImage = url("app_logo.svg")
    backgroundRepeat = BackgroundRepeat.noRepeat
    backgroundSize = size
    backgroundPosition = GeometryPosition.center
}

fun PropertiesBuilder.primaryContentSizeProps() {
    width = 100.pct
    maxWidth = 500.px
    marginTop = 24.px
}

private val textDropShadow = dropShadow(offsetX = 0.px, offsetY = 4.px, blurRadius = 32.px)
