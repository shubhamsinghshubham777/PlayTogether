package com.playtogether.kmp.server.repositories

import com.playtogether.kmp.data.models.User
import com.playtogether.kmp.server.UserNotFoundException
import com.playtogether.kmp.server.dbQuery
import com.playtogether.kmp.server.tables.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

interface UserRepository {
    suspend fun getUserByEmail(email: String): User
}

class UserRepositoryImpl : UserRepository {
    override suspend fun getUserByEmail(email: String): User = dbQuery {
        UserTable.select { UserTable.email eq email }.firstOrNull()?.toDUser() ?: throw UserNotFoundException
    }
}

fun ResultRow.toDUser() = User(
    name = this[UserTable.name],
    email = this[UserTable.email],
    avatarUrl = this[UserTable.avatarUrl],
    hashedPassword = this[UserTable.hashedPassword],
    salt = this[UserTable.salt]
)
