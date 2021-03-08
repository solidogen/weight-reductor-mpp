package com.spyrdonapps.weightreductor.backend.util.extensions

import com.spyrdonapps.weightreductor.backend.CiVariables

/**
 * CiVariables.kt file is generated by generateCiVariables gradle task before backend module gets built.
 * */
val CiVariables.isCiBuild: Boolean
    get() = when (IS_CI_BUILD) {
        "true" -> true
        "false" -> false
        else -> error("IS_CI_BUILD is not a boolean. Actual value: $IS_CI_BUILD")
    }