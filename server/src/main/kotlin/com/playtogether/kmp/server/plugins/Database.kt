package com.playtogether.kmp.server.plugins

import com.playtogether.kmp.data.util.Constants
import com.playtogether.kmp.server.tables.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    fun initDb() = Database.connect(
        HikariDataSource(
            HikariConfig().apply {
                driverClassName = Constants.Server.DBDriver
                jdbcUrl = System.getenv(Constants.Server.SecretKeys.DBUrl)
                username = System.getenv(Constants.Server.SecretKeys.DBUser)
                password = System.getenv(Constants.Server.SecretKeys.DBPassword)
            }
        )
    ).apply {
        transaction(this) {
            SchemaUtils.createMissingTablesAndColumns(UserTable)
        }
    }
}
