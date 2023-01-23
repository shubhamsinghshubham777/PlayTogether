package com.playtogether.kmp.presentation.util

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    object Empty : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure(val exception: Exception) : UIState<Nothing>()
}
