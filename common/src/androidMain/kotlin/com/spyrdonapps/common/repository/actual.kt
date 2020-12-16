package com.spyrdonapps.common.repository

import android.content.Context
import android.os.Build
import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger

lateinit var appContext: Context
lateinit var localBackendIp: String

actual fun getLogger(): Logger = LogcatLogger()

actual val localhostDomain = if (isEmulator) {
    "10.0.2.2"
} else {
    localBackendIp
}

private val isEmulator: Boolean
    get() = Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.PRODUCT.contains("sdk_google")
            || Build.PRODUCT.contains("google_sdk")
            || Build.PRODUCT.contains("sdk")
            || Build.PRODUCT.contains("sdk_x86")
            || Build.PRODUCT.contains("vbox86p")
            || Build.PRODUCT.contains("emulator")
            || Build.PRODUCT.contains("simulator")
