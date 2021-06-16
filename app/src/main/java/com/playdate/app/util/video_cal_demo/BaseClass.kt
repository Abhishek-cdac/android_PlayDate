package com.playdate.app.util.video_cal_demo

import android.app.Application
import com.playdate.app.BuildConfig
import timber.log.Timber

class BaseClass : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        SettingsProvider.initConnectycubeCredentials(this)
        SettingsProvider.initChatConfiguration()
    }
}