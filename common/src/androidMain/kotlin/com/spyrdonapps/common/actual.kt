package com.spyrdonapps.common

import android.content.Context
import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.util.utils.ClientType

lateinit var appContext: Context

actual fun getLogger(): Logger = LogcatLogger()

actual val clientType: ClientType = ClientType.Android