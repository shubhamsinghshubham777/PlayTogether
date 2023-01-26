package com.playtogether.kmp.data.sources.local

import com.playtogether.kmp.PTDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.await

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver = initSqlDriver(schema = PTDatabase.Schema).await()
}
