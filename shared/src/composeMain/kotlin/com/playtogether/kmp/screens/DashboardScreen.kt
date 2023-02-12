package com.playtogether.kmp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    toggleTheme: () -> Unit,
    logout: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Dashboard Screen")
        Button(onClick = toggleTheme) {
            Text("Toggle theme")
        }
        Button(onClick = logout) {
            Text("Logout")
        }
    }
}
