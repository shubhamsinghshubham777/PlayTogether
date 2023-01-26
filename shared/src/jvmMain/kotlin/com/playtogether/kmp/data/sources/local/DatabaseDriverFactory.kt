package com.playtogether.kmp.data.sources.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.nio.file.Paths

actual class DatabaseDriverFactory {
    @Suppress("NewApi")
    actual suspend fun createDriver(): SqlDriver {
        val path = Paths.get("").toAbsolutePath().toString()
        return JdbcSqliteDriver(url = "jdbc:sqlite:$path/shared/src/commonMain/sqldelight/databases/1.db")
    }
}
