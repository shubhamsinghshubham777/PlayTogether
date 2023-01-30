package com.playtogether.kmp.presentation.viewmodels

import com.playtogether.kmp.data.repositories.AuthRepository
import com.playtogether.kmp.data.repositories.SettingsRepository
import com.playtogether.kmp.presentation.util.SharedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : SharedViewModel() {
    private val _isUserLoggedIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private val _isDarkModeOn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isDarkModeOn = _isDarkModeOn.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            authRepository.isUserLoggedIn().collect { _isUserLoggedIn.emit(it) }
        }
        viewModelScope.launch(dispatcher) {
            settingsRepository.getIsDarkThemeOn().collect { _isDarkModeOn.emit(it) }
        }
    }

    fun setIsDarkThemeOn(isDarkThemeOn: Boolean) {
        viewModelScope.launch(dispatcher) {
            settingsRepository.setIsDarkThemeOn(isDarkThemeOn = isDarkThemeOn)
        }
    }
}
