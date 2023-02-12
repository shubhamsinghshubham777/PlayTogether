package com.playtogether.kmp.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import com.playtogether.kmp.PTDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsRepository {
    suspend fun getIsDarkThemeOn(): Flow<Boolean>
    suspend fun setIsDarkThemeOn(isDarkThemeOn: Boolean)
}

class SettingsRepositoryImpl(
    private val database: Deferred<PTDatabase>
) : SettingsRepository {
    override suspend fun getIsDarkThemeOn(): Flow<Boolean> {
        return database.await()
            .pTDatabaseQueries
            .getIsDarkModeOn()
            .asFlow()
            .map {
                it.executeAsOneOrNull()?.isDarkModeOn == 1L
            }
    }

    override suspend fun setIsDarkThemeOn(isDarkThemeOn: Boolean) {
        val db = database.await().pTDatabaseQueries

        val entryExists = db.getIsDarkModeOn().executeAsOneOrNull() != null

        if (entryExists) db.updateIsDarkModeOn(if (isDarkThemeOn) 1 else 0)
        else db.insertIsDarkModeOn(if (isDarkThemeOn) 1 else 0)
    }
}
