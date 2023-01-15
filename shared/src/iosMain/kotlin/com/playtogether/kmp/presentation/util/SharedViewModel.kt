package com.playtogether.kmp.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual abstract class SharedViewModel {
    actual val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    protected actual open fun onCleared() {}
}
