package com.playtogether.kmp.data.sources.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.util.Constants

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema = PTDatabase.Schema, name = Constants.SQLDelight.DBName)
    }
}
