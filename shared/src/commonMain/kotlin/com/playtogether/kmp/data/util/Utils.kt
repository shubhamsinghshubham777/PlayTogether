package com.playtogether.kmp.data.util

import co.touchlab.kermit.Logger
import com.playtogether.kmp.data.models.server.RouteExceptionResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> safeApiCall(block: () -> HttpResponse): Resource<T> {
    return try {
        val response = block()
        Logger.i { response.toString() }
        if (response.status == HttpStatusCode.OK) Resource.Success(data = response.body())
        else throw Exception(response.body<RouteExceptionResponse>().message)
    } catch (e: Exception) {
        Resource.Failure(exception = e)
    }
}
