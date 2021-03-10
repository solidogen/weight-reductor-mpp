package com.spyrdonapps.weightreductor.android

import android.app.Application
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.repository.appContext
import com.spyrdonapps.weightreductor.BuildConfig
import com.spyrdonapps.weightreductor.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class WeightReductorAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initKoin(enableNetworkLogs = true, forcedEnvironment = forcedEnvironment) {
            androidLogger()
            androidContext(this@WeightReductorAndroidApp)
            modules(appModule)
        }
    }

    private val forcedEnvironment: Environment
        get() = when (BuildConfig.API_URL) {
            Environment.localAndroidEmulator -> Environment.LocalAndroid
            Environment.dev -> Environment.Dev
            Environment.prod -> Environment.Prod
            else -> error("No environment for url: ${BuildConfig.API_URL}")
        }
}