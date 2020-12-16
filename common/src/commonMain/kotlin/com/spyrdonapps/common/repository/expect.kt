package com.spyrdonapps.common.repository

import co.touchlab.kermit.Logger

expect fun getLogger(): Logger

/**
 * localhost for js
 * for android depends if is it emulator or not
 * */
expect val localhostDomain: String