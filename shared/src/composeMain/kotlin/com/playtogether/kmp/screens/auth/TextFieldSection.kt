package com.playtogether.kmp.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.playtogether.kmp.components.PTOutlinedTextField
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.RegexPatterns
import com.playtogether.kmp.presentation.util.UIState
import com.playtogether.kmp.spacing
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextFieldSection(
    modifier: Modifier = Modifier,
    onLogin: (email: String, password: String) -> Unit,
    onRegister: (email: String, password: String) -> Unit,
    loginStateFlow: Flow<UIState<AuthResponse>>,
    registerStateFlow: Flow<UIState<AuthResponse>>
) {
    val loginState by loginStateFlow.collectAsState(UIState.Empty)
    val registerState by registerStateFlow.collectAsState(UIState.Empty)

    var email by rememberSaveable { mutableStateOf("") }
    var isEmailValid by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordSecured by rememberSaveable { mutableStateOf(true) }
    var isPasswordValid by rememberSaveable { mutableStateOf(false) }

    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var isRepeatPasswordSecured by rememberSaveable { mutableStateOf(true) }
    var isRepeatPasswordValid by rememberSaveable { mutableStateOf(false) }

    var authType by rememberSaveable { mutableStateOf(AuthType.Login) }
    var areAuthCredentialsValid by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(email) { isEmailValid = RegexPatterns.Email.matches(email) }

    LaunchedEffect(password) { isPasswordValid = RegexPatterns.Password.matches(password) }

    LaunchedEffect(repeatPassword, authType) { isRepeatPasswordValid = repeatPassword == password }

    LaunchedEffect(email, password, repeatPassword, authType) {
        areAuthCredentialsValid = when (authType) {
            AuthType.Register -> isEmailValid && isPasswordValid && isRepeatPasswordValid
            AuthType.Login -> isEmailValid && isPasswordValid
        }
    }

    @Composable
    fun TFTrailingIcon(
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (isSelected) Icons.Default.Visibility
                else Icons.Default.VisibilityOff,
                contentDescription = null
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.huge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val tfTopPadding = MaterialTheme.spacing.large
        val tfModifier = remember {
            Modifier
                .fillMaxWidth(TF_SECTION_WIDTH_FACTOR)
                .padding(top = tfTopPadding)
        }
        val errorTextStyle = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.error
        )
        val focusManager = LocalFocusManager.current
        PTOutlinedTextField(
            modifier = tfModifier,
            value = email,
            onValueChange = { email = it },
            label = { Text(Constants.Auth.EmailLabel) },
            singleLine = true,
            isError = !isEmailValid && email.isNotBlank(),
            supportingText = if (!isEmailValid && email.isNotBlank()) {
                {
                    Text(
                        text = Constants.Auth.InvalidEmail,
                        style = errorTextStyle
                    )
                }
            } else null,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )
        PTOutlinedTextField(
            modifier = tfModifier,
            value = password,
            onValueChange = { password = it },
            label = { Text(Constants.Auth.PasswordLabel) },
            visualTransformation = if (isPasswordSecured) PasswordVisualTransformation()
            else VisualTransformation.None,
            trailingIcon = {
                TFTrailingIcon(
                    isSelected = isPasswordSecured,
                    onClick = { isPasswordSecured = !isPasswordSecured }
                )
            },
            singleLine = true,
            isError = !isPasswordValid && password.isNotBlank(),
            supportingText = if (!isPasswordValid && password.isNotBlank()) {
                {
                    Text(
                        text = Constants.Auth.InvalidPassword,
                        style = errorTextStyle
                    )
                }
            } else null,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = if (authType == AuthType.Register) ImeAction.Next else ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onDone = { focusManager.clearFocus() }
            )
        )
        AnimatedVisibility(authType == AuthType.Register) {
            PTOutlinedTextField(
                modifier = tfModifier,
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                label = { Text(Constants.Auth.RepeatPasswordLabel) },
                visualTransformation = if (isRepeatPasswordSecured) PasswordVisualTransformation()
                else VisualTransformation.None,
                trailingIcon = {
                    TFTrailingIcon(
                        isSelected = isRepeatPasswordSecured,
                        onClick = { isRepeatPasswordSecured = !isRepeatPasswordSecured }
                    )
                },
                singleLine = true,
                isError = !isRepeatPasswordValid,
                supportingText = if (!isRepeatPasswordValid) {
                    {
                        Text(
                            text = Constants.Auth.InvalidRepeatPassword,
                            style = errorTextStyle
                        )
                    }
                } else null,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
        }
        AnimatedVisibility(
            visible = when (authType) {
                AuthType.Register -> registerState is UIState.Failure
                AuthType.Login -> loginState is UIState.Failure
            },
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(TF_SECTION_WIDTH_FACTOR)
                    .padding(top = MaterialTheme.spacing.huge),
                text = when (authType) {
                    AuthType.Register -> {
                        (registerState as? UIState.Failure)?.let {
                            it.exception.message.orEmpty()
                        } ?: ""
                    }

                    AuthType.Login -> {
                        (loginState as? UIState.Failure)?.let {
                            it.exception.message.orEmpty()
                        } ?: ""
                    }
                },
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(TF_SECTION_WIDTH_FACTOR)
                .padding(vertical = MaterialTheme.spacing.huge),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    when (authType) {
                        AuthType.Register -> onRegister(email, password)
                        AuthType.Login -> onLogin(email, password)
                    }
                },
                enabled = areAuthCredentialsValid && loginState !is UIState.Loading &&
                        registerState !is UIState.Loading
            ) {
                AnimatedContent(
                    loginState is UIState.Loading || registerState is UIState.Loading
                ) { isLoading ->
                    if (isLoading) CircularProgressIndicator()
                    else Text(authType.name)
                }
            }
            TextButton(
                onClick = { authType = authType.toggled() },
                enabled = loginState !is UIState.Loading && registerState !is UIState.Loading
            ) {
                Text(
                    when (authType) {
                        AuthType.Register -> Constants.Auth.LoginAuthTypeMessage
                        AuthType.Login -> Constants.Auth.RegisterAuthTypeMessage
                    },
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

private const val TF_SECTION_WIDTH_FACTOR = 0.7f
