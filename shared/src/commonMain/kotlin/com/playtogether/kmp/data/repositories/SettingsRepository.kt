package com.playtogether.kmp.data.repositories

import com.playtogether.kmp.data.util.Constants
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface SettingsRepository {
    suspend fun getIsDarkThemeOn(): Flow<Boolean>
    suspend fun setIsDarkThemeOn(isDarkThemeOn: Boolean)
}

class SettingsRepositoryImpl(
    private val settings: Settings
) : SettingsRepository {
    private val isDarkThemeOnState: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        isDarkThemeOnState.update {
            settings.getBoolean(Constants.SharedPrefKeys.IsDarkThemeOn, defaultValue = true)
        }
    }

    override suspend fun getIsDarkThemeOn(): Flow<Boolean> {
        return isDarkThemeOnState
    }

    override suspend fun setIsDarkThemeOn(isDarkThemeOn: Boolean) {
        settings.putBoolean(Constants.SharedPrefKeys.IsDarkThemeOn, isDarkThemeOn)
        isDarkThemeOnState.emit(settings.getBoolean(Constants.SharedPrefKeys.IsDarkThemeOn))
    }
}
