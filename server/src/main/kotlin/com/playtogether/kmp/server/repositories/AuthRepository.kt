package com.playtogether.kmp.server.repositories

import com.playtogether.kmp.server.DBUtils
import com.playtogether.kmp.server.UserAlreadyExistsException
import com.playtogether.kmp.server.dbQuery
import com.playtogether.kmp.server.tables.UserTable
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import java.util.UUID

interface AuthRepository {
    /**
     * Registers a user if no user already exists with the same credentials in the system
     * @param email E-mail of the user to be registered
     * @param hashedPassword Hashed password of the user to be registered
     * @param salt String to be used to obfuscate the password
     * @throws UserAlreadyExistsException if the given credentials are already present in the system
     */
    suspend fun register(
        email: String,
        hashedPassword: String,
        salt: String
    )

    /**
     * Updates refresh token of a user if his previous refresh token has expired. Once a user is
     * registered and logged in, this method should ideally be called once every 7 days since a
     * refresh token has an expiry time of 7 days. Otherwise, this method will be called on every
     * login or signup. Method call is ignored if [previousExpiresAt] has not expired yet (i.e.
     * smaller than or equal to [expiresAt]).
     *
     * @param email used to identify the user whose refresh token we need to update
     * @param previousExpiresAt already saved value of expiresAt for the given user
     * (in milliseconds)
     * @param expiresAt depending on whether [previousExpiresAt] is null, this value can either be
     * the expected milliseconds on which the [previousExpiresAt] value should expire, or if the
     * previous value is null, then this depicts the expiry milliseconds of the newly generated
     * refresh token (i.e. after 7 days).
     */
    suspend fun updateRefreshToken(
        email: String,
        previousExpiresAt: Long? = null,
        expiresAt: Long = Instant.fromEpochMilliseconds(
            previousExpiresAt ?: Clock.System.now().toEpochMilliseconds()
        ).plus(7, DateTimeUnit.DAY, TimeZone.UTC).toEpochMilliseconds()
    )
}

class AuthRepositoryImpl : AuthRepository {
    override suspend fun register(
        email: String,
        hashedPassword: String,
        salt: String
    ): Unit = dbQuery {
        DBUtils.checkIfUserExistsInDb(
            email = email,
            ifUserNotExists = {
                UserTable.insert {
                    it[UserTable.email] = email
                    it[UserTable.hashedPassword] = hashedPassword
                    it[UserTable.salt] = salt
                }
            },
            ifUserExists = { throw UserAlreadyExistsException }
        )
    }

    override suspend fun updateRefreshToken(
        email: String,
        previousExpiresAt: Long?,
        expiresAt: Long
    ) {
        dbQuery {
            previousExpiresAt?.let { nnPreviousExpiresAt ->
                if (nnPreviousExpiresAt <= expiresAt) return@dbQuery
            }
            val refreshToken = UUID.randomUUID().toString()
            UserTable.update(
                where = { UserTable.email eq email }
            ) { table ->
                table[UserTable.refreshToken] = refreshToken
                table[UserTable.expiresAt] = expiresAt
            }
        }
    }
}
