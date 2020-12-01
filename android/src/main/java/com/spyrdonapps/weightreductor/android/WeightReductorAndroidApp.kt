package com.spyrdonapps.weightreductor.android

import android.app.Application
import com.spyrdonapps.common.di.commonModule
import com.spyrdonapps.common.di.initKoin
import com.spyrdonapps.common.repository.appContext
import com.spyrdonapps.common.repository.localBackendIp
import com.spyrdonapps.weightreductor.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class WeightReductorAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        localBackendIp = BuildConfig.LOCAL_BACKEND_IP
        initKoin {
            androidLogger()
            androidContext(this@WeightReductorAndroidApp)
            modules(/*appModule, */commonModule)
        }
    }
}