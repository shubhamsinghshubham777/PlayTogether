package com.playtogether.kmp.web.ui.screens.auth

import com.playtogether.kmp.data.types.AuthType
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.web.util.currentValue
import mui.material.TextField
import mui.material.Typography
import mui.material.styles.Theme
import mui.material.styles.useTheme
import mui.system.sx
import react.FC
import react.Props
import react.create
import react.dom.onChange
import web.html.InputType

external interface TextFieldSectionProps : Props {
    var email: String
    var onEmailChange: (newEmail: String) -> Unit
    var isEmailValid: Boolean

    var password: String
    var onPasswordChange: (newPassword: String) -> Unit
    var isPasswordValid: Boolean

    var isPasswordSecure: Boolean
    var onPasswordEndAdornmentClick: () -> Unit

    var repeatPassword: String
    var onRepeatPasswordChange: (newRepeatPassword: String) -> Unit
    var isRepeatPasswordValid: Boolean

    var isRepeatPasswordSecure: Boolean
    var onRepeatPasswordEndAdornmentClick: () -> Unit

    var authType: AuthType
}

val TextFieldSection = FC<TextFieldSectionProps> { props ->
    val theme = useTheme<Theme>()
    // E-mail TextField
    TextField {
        sx { primaryContentSizeProps() }
        type = InputType.email
        label = Typography.create { +Constants.Auth.EmailLabel }
        onChange = { props.onEmailChange(it.currentValue) }
        error = !props.isEmailValid.or(props.email.isEmpty())
        helperText = if (props.isEmailValid.or(props.email.isEmpty())) null
        else theme.textFieldErrorText(Constants.Auth.InvalidEmail)
    }
    // Password TextField
    SecureTextInput {
        sx { primaryContentSizeProps() }
        isSecure = props.isPasswordSecure
        labelName = Constants.Auth.PasswordLabel
        onEndAdornmentClick = props.onPasswordEndAdornmentClick
        onChange = { props.onPasswordChange(it.currentValue) }
        error = !props.isPasswordValid.or(props.password.isEmpty())
        errorMessage = if (props.isPasswordValid.or(props.password.isEmpty())) null
        else Constants.Auth.InvalidPassword
    }
    // Repeat Password TextField
    if (props.authType == AuthType.Register) {
        SecureTextInput {
            sx { primaryContentSizeProps() }
            initialValue = props.repeatPassword
            isSecure = props.isRepeatPasswordSecure
            labelName = Constants.Auth.RepeatPasswordLabel
            onEndAdornmentClick = props.onRepeatPasswordEndAdornmentClick
            onChange = { props.onRepeatPasswordChange(it.currentValue) }
            error = !props.isRepeatPasswordValid.or(props.repeatPassword.isEmpty())
            errorMessage = if (props.isRepeatPasswordValid.or(props.repeatPassword.isEmpty())) null
            else Constants.Auth.InvalidRepeatPassword
        }
    }
}
