package com.peto.droidmorning

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class DroidMorningApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@DroidMorningApp)
            androidLogger(Level.DEBUG)
        }
    }
}
