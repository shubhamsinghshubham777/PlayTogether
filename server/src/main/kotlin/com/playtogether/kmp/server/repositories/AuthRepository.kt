package com.playtogether.kmp.server.repositories

import com.playtogether.kmp.server.UserAlreadyExistsDuringSignUpException
import com.playtogether.kmp.server.dbQuery
import com.playtogether.kmp.server.tables.UserTable
import org.jetbrains.exposed.sql.insert

interface AuthRepository {
    /**
     * Registers a user if no user already exists with the same credentials in the system
     * @param email E-mail of the user to be registered
     * @param hashedPassword Hashed password of the user to be registered
     * @param salt String to be used to obfuscate the password
     * @throws UserAlreadyExistsDuringSignUpException if the given credentials are already present in the system
     */
    suspend fun register(
        email: String,
        hashedPassword: String,
        salt: String
    )
}

class AuthRepositoryImpl : AuthRepository {
    override suspend fun register(
        email: String,
        hashedPassword: String,
        salt: String
    ): Unit = dbQuery {
        val dbResponse = UserTable.insert {
            it[UserTable.email] = email
            it[UserTable.hashedPassword] = hashedPassword
            it[UserTable.salt] = salt
        }
        if (dbResponse.insertedCount == 0) throw UserAlreadyExistsDuringSignUpException
    }
}
