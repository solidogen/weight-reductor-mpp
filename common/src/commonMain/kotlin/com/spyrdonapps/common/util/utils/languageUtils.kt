package com.spyrdonapps.common.util.utils

val <T> T.exhaustive: T
    get() = this

fun interface Action {
    fun invoke()
}

fun interface ActionF<T> {
    fun invoke(item: T)
}