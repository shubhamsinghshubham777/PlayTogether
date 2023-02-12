package com.playtogether.kmp.android.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.playtogether.kmp.android.ui.theme.PTTheme
import com.playtogether.kmp.android.ui.util.DevicePreviews
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.presentation.util.UIState

@Composable
fun AuthScreen(
    loginState: UIState<AuthResponse>,
    registerState: UIState<AuthResponse>,
    onLogin: (email: String, password: String) -> Unit,
    onRegister: (email: String, password: String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = email,
            onValueChange = { email = it }
        )
        TextField(
            value = password,
            onValueChange = { password = it }
        )
        TextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it }
        )
        Button(
            onClick = {
                onLogin(email, password)
            }
        ) {
            Text(text = "Login")
        }
        AnimatedVisibility(visible = loginState is UIState.Failure) {
            Text(
                text = (loginState as? UIState.Failure)?.exception?.message.orEmpty(),
                color = MaterialTheme.colors.error
            )
        }
    }
}

@DevicePreviews
@Composable
private fun AuthScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AuthScreen(
                loginState = UIState.Failure(Exception("Test exception")),
                registerState = UIState.Empty,
                onLogin = { email, password -> },
                onRegister = { email, password -> }
            )
        }
    }
}