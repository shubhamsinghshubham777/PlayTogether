package com.playtogether.kmp.server

import com.playtogether.kmp.data.models.User
import com.playtogether.kmp.data.models.server.MessageResponse
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.server.repositories.toDUser
import com.playtogether.kmp.server.tables.UserTable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.postgresql.util.PSQLException

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }

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
            MessageResponse(
                message = when (e) {
                    is PSQLException -> "PSQLException...."
                    is PTException -> e.errorMessage
                    else -> Constants.Server.Exceptions.generic(reason = e.message)
                }
            )
        )
        null
    }
}

object AWSUtils {
    val bucketName = System.getenv()[Constants.Server.SecretKeys.AWSBucketName]
        ?: throw IllegalEnvironmentVariableException(Constants.Server.SecretKeys.AWSBucketName)

    val region = System.getenv()[Constants.Server.SecretKeys.AWSRegion]
        ?: throw IllegalEnvironmentVariableException(Constants.Server.SecretKeys.AWSRegion)

    /**
     * Returns a public AWS URL for the given file name present in AWS S3 bucket.
     */
    fun s3FileUrl(fileName: String): String {
        return "https://$bucketName.s3.$region.amazonaws.com/$fileName"
    }

    fun keyFromUrl(url: String?): String? {
        return url?.substringAfterLast("/")
    }
}

suspend fun ApplicationCall.fetchEmailFromToken(): String {
    val principal = principal<JWTPrincipal>()

    val email = principal?.getClaim(
        Constants.Server.JWTClaimEmail,
        String::class
    )

    return dbQuery {
        DBUtils.checkIfUserExistsInDb(
            email = email ?: throw PTException(Constants.Server.Exceptions.InvalidAuthToken),
            ifUserExists = {
                return@dbQuery email
            },
            ifUserNotExists = {
                throw UserNotFoundException
            }
        )
        throw PTException(Constants.Server.Exceptions.InvalidAuthToken)
    }
}

fun ApplicationCall.fetchParam(name: String): String {
    return parameters[name] ?: throw InvalidParameterException(paramName = name)
}

object DBUtils {
    inline fun checkIfUserExistsInDb(
        email: String,
        ifUserNotExists: () -> Unit = { throw Exception(Constants.Server.Exceptions.UserNotFound) },
        ifUserExists: (User) -> Unit = {}
    ) {
        val user = UserTable.select { UserTable.email eq email }.firstOrNull()?.toDUser()
        user?.let { nnUser -> ifUserExists(nnUser) } ?: run(ifUserNotExists)
    }
}
