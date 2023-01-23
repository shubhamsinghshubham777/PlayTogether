package com.playtogether.kmp.android.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import org.koin.androidx.compose.get

@Composable
fun PTApp() {
    val mainViewModel = get<MainViewModel>()
    val isLoggedIn by mainViewModel.isUserLoggedIn.collectAsState()
    Text("Hello, Android! Are you logged in?: $isLoggedIn")
}
