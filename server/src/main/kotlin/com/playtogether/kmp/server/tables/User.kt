package com.playtogether.kmp.server.tables

import com.playtogether.kmp.data.models.User
import org.jetbrains.exposed.sql.Table

/**
 * This [Table] represents an instance of [User] in an SQL DB.
 */
object UserTable : Table() {
    val name = varchar(name = "name", length = 128).nullable()
    val email = varchar(name = "email", length = 128)
    val avatarUrl = mediumText(name = "avatarUrl").nullable()
    val salt = text(name = "salt")
    val hashedPassword = text(name = "hashedPassword")

    override val primaryKey: PrimaryKey = PrimaryKey(email)
}
