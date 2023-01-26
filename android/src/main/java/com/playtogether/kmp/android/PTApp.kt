package com.playtogether.kmp.android

import android.app.Application
import com.playtogether.kmp.di.dataSourceModuleAndroid
import com.playtogether.kmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class PTApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PTApp)
            modules(dataSourceModuleAndroid)
        }
    }
}
