package com.playtogether.kmp.data.sources.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    suspend fun createDriver(): SqlDriver
}
