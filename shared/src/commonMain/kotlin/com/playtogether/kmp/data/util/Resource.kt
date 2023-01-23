package com.playtogether.kmp.data.util

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Failure(val exception: Exception) : Resource<Nothing>()
}
