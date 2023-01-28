package com.playtogether.kmp.presentation.viewmodels

import com.playtogether.kmp.data.models.server.AuthResponse
import com.playtogether.kmp.data.repositories.AuthRepository
import com.playtogether.kmp.data.util.Resource
import com.playtogether.kmp.presentation.util.SharedViewModel
import com.playtogether.kmp.presentation.util.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val authRepository: AuthRepository
) : SharedViewModel() {
    private val _loginState: MutableStateFlow<UIState<AuthResponse>> = MutableStateFlow(UIState.Empty)
    val loginState = _loginState.asStateFlow()

    private val _registerState: MutableStateFlow<UIState<AuthResponse>> = MutableStateFlow(UIState.Empty)
    val registerState = _registerState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            _loginState.emit(UIState.Loading)
            when (val response = authRepository.login(email = email, password = password)) {
                is Resource.Failure -> _loginState.emit(UIState.Failure(exception = response.exception))
                is Resource.Success -> _loginState.emit(UIState.Success(data = response.data))
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            _registerState.emit(UIState.Loading)
            when(val response = authRepository.register(email = email, password = password)) {
                is Resource.Failure -> _registerState.emit(UIState.Failure(exception = response.exception))
                is Resource.Success ->  _registerState.emit(UIState.Success(data = response.data))
            }
        }
    }

    fun logout() {
        viewModelScope.launch(dispatcher) {
            authRepository.logout()
        }
    }
}
