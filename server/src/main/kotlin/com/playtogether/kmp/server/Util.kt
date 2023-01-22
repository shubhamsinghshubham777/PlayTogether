package com.playtogether.kmp.server

import com.playtogether.kmp.data.util.Constants
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.util.regex.Pattern
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
            RouteException(
                message = if (e is PTException) e.errorMessage
                else Constants.Server.Exceptions.Generic + e.message.orEmpty()
            )
        )
        null
    }
}

object RegexPatterns {
    val Email: Pattern = Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    )

    val Password: Pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$"
    )
}
