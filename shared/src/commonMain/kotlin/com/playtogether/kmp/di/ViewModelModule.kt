package com.playtogether.kmp.di

import com.playtogether.kmp.presentation.viewmodels.AuthViewModel
import com.playtogether.kmp.presentation.viewmodels.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel() }
    single { AuthViewModel() }
}

class ViewModelDIHelper : KoinComponent {
    val mainViewModel: MainViewModel = get()
    val authViewModel: AuthViewModel = get()
}