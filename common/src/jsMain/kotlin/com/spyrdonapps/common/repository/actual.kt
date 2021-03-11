package com.spyrdonapps.common.repository

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Logger
import com.spyrdonapps.common.util.utils.ClientType

actual fun getLogger(): Logger = CommonLogger()

actual val clientType: ClientType = ClientType.Javascript