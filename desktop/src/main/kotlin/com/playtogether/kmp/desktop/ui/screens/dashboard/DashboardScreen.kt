package com.playtogether.kmp.desktop.ui.screens.dashboard

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.playtogether.kmp.desktop.ui.theme.PTTheme

@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    isDarkModeOn: Boolean?,
    onToggleTheme: (isDarkModeOn: Boolean) -> Unit,
) {
    Column {
        Text("Dashboard Screen")
        Button(onClick = onLogout) {
            Text("Logout")
        }
        Text("isDarkModeOn: $isDarkModeOn")
        Button(onClick = { onToggleTheme(isDarkModeOn?.not() ?: false) }) {
            Text("Toggle Theme")
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    PTTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            DashboardScreen(
                onLogout = {},
                isDarkModeOn = false,
                onToggleTheme = {}
            )
        }
    }
}
