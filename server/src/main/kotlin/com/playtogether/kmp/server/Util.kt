package com.playtogether.kmp.server

import com.playtogether.kmp.data.models.server.RouteExceptionResponse
import com.playtogether.kmp.data.util.Constants
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

suspend fun <T> PipelineContext<*, ApplicationCall>.safeCall(block: suspend () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        call.respond(
            status = when (e) {
                is InvalidParameterException -> HttpStatusCode.BadRequest
                is PTException -> HttpStatusCode.Conflict
                else -> HttpStatusCode.InternalServerError
            },
            RouteExceptionResponse(
                message = if (e is PTException) e.errorMessage
                else Constants.Server.Exceptions.Generic + e.message.orEmpty()
            )
        )
        null
    }
}
