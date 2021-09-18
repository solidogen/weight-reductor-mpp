package com.spyrdonapps.weightreductor.frontend.utils

import org.jetbrains.compose.web.attributes.AttrsBuilder

fun String.classesArray(): Array<String> = split(" ").toTypedArray()

fun AttrsBuilder<*>.tailwindClasses(multiclassString: String) {
    classes(*multiclassString.classesArray())
}