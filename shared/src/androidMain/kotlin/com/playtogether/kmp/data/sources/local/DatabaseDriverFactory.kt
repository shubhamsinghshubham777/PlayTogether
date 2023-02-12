package com.playtogether.kmp.data.sources.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.util.Constants

actual class DatabaseDriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = PTDatabase.Schema,
            context = context,
            name = Constants.SQLDelight.DBName
        )
    }
}
