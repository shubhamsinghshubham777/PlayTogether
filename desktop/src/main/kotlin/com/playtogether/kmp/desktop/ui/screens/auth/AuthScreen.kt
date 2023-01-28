package com.playtogether.kmp.desktop.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.data.util.RegexPatterns
import com.playtogether.kmp.desktop.ui.components.PTTextField
import com.playtogether.kmp.desktop.ui.theme.PTTheme
import com.playtogether.kmp.presentation.util.UIState

@Composable
fun AuthScreen(
    loginState: UIState<AuthResponse>,
    onLogin: (email: String, password: String) -> Unit,
    onRegister: (email: String, password: String) -> Unit,
) {
    var isUserRegistering by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    fun areCredentialsValid(): Boolean {
        val isLoginValid = email.isNotBlank() && password.isNotBlank()
                && RegexPatterns.Email.matches(email) && RegexPatterns.Password.matches(password)

        val isRegisterValid = isLoginValid && repeatPassword.isNotBlank() && password == repeatPassword

        return if (isUserRegistering) isRegisterValid else isLoginValid
    }

    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        PTTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(Constants.Auth.EmailLabel) },
            singleLine = true
        )
        PTTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text(Constants.Auth.PasswordLabel) },
            singleLine = true
        )
        AnimatedVisibility(visible = isUserRegistering) {
            PTTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text(Constants.Auth.RepeatPasswordLabel) },
                singleLine = true
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (isUserRegistering) {
                        onRegister(email, password)
                    } else {
                        onLogin(email, password)
                    }
                },
                enabled = areCredentialsValid()
            ) {
                when (loginState) {
                    is UIState.Loading -> CircularProgressIndicator()
                    else -> Text(if (isUserRegistering) "Register" else "Login")
                }
            }
            Column {
                TextButton(
                    onClick = { isUserRegistering = !isUserRegistering }
                ) {
                    Text(
                        if (isUserRegistering) Constants.Auth.RegisterAuthTypeMessage
                        else Constants.Auth.LoginAuthTypeMessage
                    )
                }
            }
        }
        when (loginState) {
            is UIState.Failure -> Text(
                text = loginState.exception.message.orEmpty(),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )

            else -> Box(modifier = Modifier)
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AuthScreen(
                loginState = UIState.Empty,
                onLogin = { _, _ -> },
                onRegister = { _, _ -> },
            )
        }
    }
}
