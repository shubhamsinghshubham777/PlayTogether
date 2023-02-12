package com.playtogether.kmp.web.ui.screens.auth

import mui.icons.material.Visibility
import mui.icons.material.VisibilityOff
import mui.material.FormControl
import mui.material.FormControlProps
import mui.material.FormControlVariant
import mui.material.FormHelperText
import mui.material.IconButton
import mui.material.InputLabel
import mui.material.OutlinedInput
import mui.material.Typography
import mui.material.styles.Theme
import mui.material.styles.TypographyVariant
import mui.material.styles.useTheme
import mui.system.sx
import react.FC
import react.create
import web.html.InputType

external interface SecureTextInputProps : FormControlProps {
    var initialValue: String
    var isSecure: Boolean
    var onEndAdornmentClick: () -> Unit
    var labelName: String
    var errorMessage: String?
}

val SecureTextInput = FC<SecureTextInputProps> { props ->
    val theme = useTheme<Theme>()
    FormControl {
        +props
        variant = FormControlVariant.outlined
        InputLabel { +props.labelName }
        OutlinedInput {
            value = props.initialValue
            type = if (props.isSecure) InputType.password.toString() else InputType.text.toString()
            endAdornment = IconButton.create {
                onClick = { props.onEndAdornmentClick() }
                (if (props.isSecure) Visibility else VisibilityOff) {}
            }
            label = Typography.create { +props.labelName }
        }
        props.errorMessage?.let { nnErrorMessage ->
            FormHelperText {
                +theme.textFieldErrorText(nnErrorMessage)
            }
        }
    }
}

fun Theme.textFieldErrorText(text: String) = Typography.create {
    sx { color = this@textFieldErrorText.palette.error.main }
    variant = TypographyVariant.caption
    +text
}
