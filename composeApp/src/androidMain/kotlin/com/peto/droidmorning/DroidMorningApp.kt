package com.peto.droidmorning

import android.app.Application
import org.koin.android.ext.koin.androidContext

class DroidMorningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            initNapier()
        }
        initKoin {
            androidContext(this@DroidMorningApp)
        }
    }
}
