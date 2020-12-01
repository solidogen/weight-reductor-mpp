package com.spyrdonapps.common.repository

import co.touchlab.kermit.Logger

expect fun getLogger(): Logger

expect val localhostDomain: String