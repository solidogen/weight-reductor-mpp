package com.spyrdonapps.weightreductor.android

import android.app.Application
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.appContext
import com.spyrdonapps.common.clientType
import com.spyrdonapps.weightreductor.BuildConfig
import com.spyrdonapps.weightreductor.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class WeightReductorAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        val environment = Environment.fromRawEnvironment(BuildConfig.RAW_ENVIRONMENT)
        environment.ensureAvailableForClientType(clientType = clientType)
        initKoin(
            enableNetworkLogs = true,
            environment = environment
        ) {
            androidLogger()
            androidContext(this@WeightReductorAndroidApp)
            modules(appModule)
        }
    }
}