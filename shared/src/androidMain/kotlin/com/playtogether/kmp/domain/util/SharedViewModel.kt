package com.playtogether.kmp.domain.util

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.ViewModel as AndroidViewModel
import androidx.lifecycle.viewModelScope as androidViewModelScope

actual abstract class SharedViewModel: AndroidViewModel() {
    actual val viewModelScope: CoroutineScope
        get() = androidViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}
