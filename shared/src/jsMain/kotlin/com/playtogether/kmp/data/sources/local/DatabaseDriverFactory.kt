package com.playtogether.kmp.data.sources.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import com.playtogether.kmp.PTDatabase
import kotlinx.coroutines.await

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver = initSqlDriver(schema = PTDatabase.Schema).await()
}
