package com.playtogether.kmp.domain.util

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Failure(val throwable: Throwable) : Resource<Nothing>()
}
