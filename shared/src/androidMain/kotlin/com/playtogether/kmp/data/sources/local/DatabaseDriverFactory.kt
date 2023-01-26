package com.playtogether.kmp.data.sources.local

import android.content.Context
import com.playtogether.kmp.PTDatabase
import com.playtogether.kmp.data.util.Constants
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = PTDatabase.Schema,
            context = context,
            name = Constants.SQLDelight.DBName
        )
    }
}
