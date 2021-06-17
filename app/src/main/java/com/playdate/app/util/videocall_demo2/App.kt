package com.connectycube.messenger

import android.app.Application
import com.connectycube.messenger.utilities.SettingsProvider
import com.playdate.app.BuildConfig
import timber.log.Timber

class AppNEW : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        SettingsProvider.initConnectycubeCredentials(this)
        SettingsProvider.initChatConfiguration()
    }
}