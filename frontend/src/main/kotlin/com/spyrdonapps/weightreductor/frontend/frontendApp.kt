package com.spyrdonapps.weightreductor.frontend

import react.child
import react.dom.render

fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        child(FrontendAppComponent)
    }
}