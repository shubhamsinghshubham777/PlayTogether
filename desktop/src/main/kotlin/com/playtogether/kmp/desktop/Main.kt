package com.playtogether.kmp.desktop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.darkColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.playtogether.kmp.di.ViewModelDIHelper
import com.playtogether.kmp.di.dataSourceModuleJVM
import com.playtogether.kmp.di.initKoin
import com.playtogether.kmp.presentation.util.UIState

fun main() {
    initKoin {
        modules(dataSourceModuleJVM)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Compose for Desktop",
            state = rememberWindowState(
                width = 300.dp,
                height = 300.dp
            )
        ) {
            val mainViewModel = ViewModelDIHelper.mainViewModel
            val authViewModel = ViewModelDIHelper.authViewModel
            val isLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
            val loginState by authViewModel.loginState.collectAsState()
            MaterialTheme(colors = darkColors()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    isLoggedIn?.let { nnIsLoggedIn ->
                        if (nnIsLoggedIn) {
                            Text("Dashboard Screen")
                        } else {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                var email by remember { mutableStateOf("") }
                                var password by remember { mutableStateOf("") }
                                TextField(
                                    value = email,
                                    onValueChange = { email = it }
                                )
                                TextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                Button(onClick = {
                                    authViewModel.login(email = email, password = password)
                                }) {
                                    when (loginState) {
                                        is UIState.Loading -> CircularProgressIndicator()
                                        else -> Text("Login")
                                    }
                                }
                                when (val state = loginState) {
                                    is UIState.Failure -> Text(state.exception.message.orEmpty())
                                    else -> Box(modifier = Modifier)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
