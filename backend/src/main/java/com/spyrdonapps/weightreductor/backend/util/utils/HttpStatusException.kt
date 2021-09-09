package com.spyrdonapps.weightreductor.backend.util.utils

import io.ktor.http.*

class HttpStatusException(val httpStatusCode: HttpStatusCode, override val message: String) :
    Exception()

fun httpStatusException(httpStatusCode: HttpStatusCode, message: String): Nothing =
    throw HttpStatusException(httpStatusCode = httpStatusCode, message = message)