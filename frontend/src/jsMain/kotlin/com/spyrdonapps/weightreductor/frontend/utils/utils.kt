package com.spyrdonapps.weightreductor.frontend.utils

import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.AttrsBuilder
import org.jetbrains.compose.web.attributes.disabled
import org.w3c.dom.HTMLButtonElement

fun String.classesArray(): Array<String> = split(" ").toTypedArray()

fun AttrsBuilder<*>.tailwindClasses(multiclassString: String) {
    classes(*multiclassString.classesArray())
}

fun AttrsBuilder<HTMLButtonElement>.setEnabled(isEnabled: Boolean) {
    if (!isEnabled) {
        disabled()
        tailwindClasses("cursor-not-allowed")
    }
}

@Suppress("RedundantSuspendModifier") // use only by Effects
suspend fun injectRawHtmlChild(elementId: String, rawHtml: String) {
    document.getElementById(elementId)
        ?.appendChild(
            document.createElement("div").apply {
                innerHTML = rawHtml
            }
        )
}