package com.playtogether.kmp.server.tables

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {
    val name = varchar(name = "name", length = 128).nullable()
    val email = varchar(name = "email", length = 128)
    val avatarUrl = mediumText(name = "avatarUrl").nullable()
    val salt = text(name = "salt")
    val hashedPassword = text(name = "hashedPassword")

    override val primaryKey: PrimaryKey = PrimaryKey(email)
}
