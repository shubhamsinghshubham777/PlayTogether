package com.playtogether.kmp.presentation.viewmodels

import com.playtogether.kmp.data.repositories.AuthRepository
import com.playtogether.kmp.presentation.util.SharedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val authRepository: AuthRepository
) : SharedViewModel() {
    private val _isUserLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()
    init {
        viewModelScope.launch(dispatcher) {
            _isUserLoggedIn.emit(authRepository.isUserLoggedIn())
        }
    }
}
