package com.spyrdonapps.weightreductor.frontend.utils

import kotlinx.css.*
import styled.injectGlobal

fun normalizeCss() {
    val styles = CssBuilder().apply {
        body {
            margin(0.px)
            padding(0.px)
        }
        // add if needed
    }
    injectGlobal(styles.toString())
}