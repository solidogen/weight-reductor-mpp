package com.spyrdonapps.common.repository

import android.content.Context
import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger

lateinit var appContext: Context

actual fun getLogger(): Logger = LogcatLogger()