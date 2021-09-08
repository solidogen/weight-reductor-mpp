package com.spyrdonapps.common

import co.touchlab.kermit.Logger
import com.spyrdonapps.common.util.utils.ClientType

expect fun getLogger(): Logger

expect val clientType: ClientType