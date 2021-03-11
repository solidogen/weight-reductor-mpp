package com.spyrdonapps.common.util.extensions

inline fun <reified T : Enum<T>> valueForNameOrNull(rawEnumName: String): T? =
    enumValues<T>().find { it.name == rawEnumName }