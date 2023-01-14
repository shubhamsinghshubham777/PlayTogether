package com.playtogether.kmp.domain.util

suspend fun <T> safeCall(block: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(data = block())
    } catch (t: Throwable) {
        Resource.Failure(throwable = t)
    }
}
