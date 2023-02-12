package com.playtogether.kmp.data.sources.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.nio.file.Paths

actual class DatabaseDriverFactory {
    @Suppress("NewApi")
    actual suspend fun createDriver(): SqlDriver {
        val path = Paths.get("").toAbsolutePath().toString()
        return JdbcSqliteDriver(url = "jdbc:sqlite:$path/../shared/src/commonMain/sqldelight/databases/1.db")
    }
}
