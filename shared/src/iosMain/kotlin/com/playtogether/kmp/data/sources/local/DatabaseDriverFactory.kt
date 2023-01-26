package com.playtogether.kmp.data.sources.local

import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.util.Constants
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema = PTDatabase.Schema, name = Constants.SQLDelight.DBName)
    }
}
