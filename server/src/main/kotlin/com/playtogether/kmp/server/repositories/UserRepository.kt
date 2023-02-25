package com.playtogether.kmp.server.repositories

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.deleteObject
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.ByteStream
import com.playtogether.kmp.data.models.User
import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.server.AWSUtils
import com.playtogether.kmp.server.DBUtils
import com.playtogether.kmp.server.PTException
import com.playtogether.kmp.server.UserNotFoundException
import com.playtogether.kmp.server.dbQuery
import com.playtogether.kmp.server.tables.UserTable
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

interface UserRepository {
    suspend fun getUserByEmail(email: String): User
    suspend fun updateUser(
        email: String,
        multipartData: MultiPartData
    ): Boolean

    suspend fun deleteUser(
        email: String
    ): Boolean
}

class UserRepositoryImpl : UserRepository {
    override suspend fun getUserByEmail(email: String): User = dbQuery {
        UserTable.select { UserTable.email eq email }.firstOrNull()?.toDUser()
            ?: throw UserNotFoundException
    }

    override suspend fun updateUser(
        email: String,
        multipartData: MultiPartData
    ): Boolean = dbQuery {
        var name: String? = null
        var avatarUrl: String? = null

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name == Constants.Server.Params.UserName) {
                        if (part.value.isBlank()) {
                            throw PTException(Constants.Server.Exceptions.BlankUserName)
                        } else {
                            name = part.value
                        }
                    }
                }

                is PartData.FileItem -> {
                    val fileName = email + (part.originalFileName as String).substring(
                        startIndex = part.originalFileName?.indexOfLast { it == '.' } ?: 0,
                        endIndex = part.originalFileName?.lastIndex?.inc() ?: 0
                    )
                    val fileBytes = part.streamProvider().readBytes()
                    S3Client
                        .fromEnvironment { region = AWSUtils.region }
                        .use { s3 ->
                            s3.putObject {
                                bucket = AWSUtils.bucketName
                                key = fileName
                                body = ByteStream.fromBytes(fileBytes)
                            }
                            avatarUrl = AWSUtils.s3FileUrl(fileName)
                        }
                }

                else -> Unit
            }
        }

        DBUtils.checkIfUserExistsInDb(email = email)

        val updateCount = UserTable.update(where = { UserTable.email eq email }) { table ->
            name?.let { table[UserTable.name] = it }
            avatarUrl?.let { table[UserTable.avatarUrl] = avatarUrl }
        }
        return@dbQuery updateCount > 0
    }

    override suspend fun deleteUser(email: String): Boolean = dbQuery {
        DBUtils.checkIfUserExistsInDb(email = email) { user ->
            AWSUtils.keyFromUrl(user.avatarUrl)?.let { nnKey ->
                S3Client
                    .fromEnvironment { region = AWSUtils.region }
                    .use { s3 ->
                        s3.deleteObject {
                            bucket = AWSUtils.bucketName
                            key = nnKey
                        }
                    }
            }
        }
        val deleteCount = UserTable.deleteWhere {
            this.email eq email
        }
        deleteCount > 0
    }
}

fun ResultRow.toDUser() = User(
    name = this[UserTable.name],
    email = this[UserTable.email],
    avatarUrl = this[UserTable.avatarUrl],
    hashedPassword = this[UserTable.hashedPassword],
    salt = this[UserTable.salt]
)
