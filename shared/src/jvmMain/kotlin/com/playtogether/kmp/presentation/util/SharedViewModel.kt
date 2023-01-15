package com.playtogether.kmp.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual abstract class SharedViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * It is useful when ViewModel observes some data, and you need to clear this subscription to prevent a leak of this
     * ViewModel.
     *
     * Note - This method doesn't do anything on JS.
     */
    protected actual open fun onCleared() {
    }
}
