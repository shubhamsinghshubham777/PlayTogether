package com.playtogether.kmp.screens.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.icons.PTIcons
import com.playtogether.kmp.presentation.util.UIState
import com.playtogether.kmp.spacing
import com.playtogether.kmp.util.navigationBarsPadding
import com.playtogether.kmp.util.rememberResponsiveWidthValue
import kotlinx.coroutines.flow.Flow

enum class AuthType {
    Login, Register;

    fun toggled() = if (this == Login) Register else Login
}

@Composable
fun AuthScreen(
    onLogin: (email: String, password: String) -> Unit,
    onRegister: (email: String, password: String) -> Unit,
    loginState: Flow<UIState<AuthResponse>>,
    registerState: Flow<UIState<AuthResponse>>,
) {
    val appLogo = remember {
        movableContentOf {
            Image(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.huge)
                    .fillMaxSize(
                        animateFloatAsState(
                            rememberResponsiveWidthValue(
                                compact = 0.3f,
                                medium = 0.2f,
                                expanded = 0.5f
                            ),
                            tween()
                        ).value
                    ),
                imageVector = PTIcons.AppLogo,
                contentDescription = null,
            )
        }
    }
    val greetings = remember { movableContentOf { GreetingSection() } }
    val textFieldSection = remember {
        movableContentOf {
            TextFieldSection(
                onLogin = onLogin,
                onRegister = onRegister,
                loginStateFlow = loginState,
                registerStateFlow = registerState,
            )
        }
    }

    rememberResponsiveWidthValue<@Composable () -> Unit>(
        compact = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                appLogo()
                greetings()
                textFieldSection()
                Spacer(Modifier.navigationBarsPadding())
            }
        },
        expanded = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                appLogo()
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    greetings()
                    textFieldSection()
                }
            }
        }
    )()
}