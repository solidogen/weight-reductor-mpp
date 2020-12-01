package com.spyrdonapps.common.repository

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Logger

actual fun getLogger(): Logger = CommonLogger()

actual val localhostDomain: String = "localhost"