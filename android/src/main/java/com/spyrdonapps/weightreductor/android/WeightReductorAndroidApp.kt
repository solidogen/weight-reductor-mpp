package com.spyrdonapps.weightreductor.android

import android.app.Application
import com.spyrdonapps.common.repository.appContext

class WeightReductorAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}