package com.spyrdonapps.weightreductor.frontend.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSpanElement

@Composable
fun DivTw(
    tailwindClasses: String? = null,
    attrs: AttrBuilderContext<HTMLDivElement>? = null,
    content: ContentBuilder<HTMLDivElement>? = null
) {
    Div(
        attrs = {
            tailwindClasses?.let { tailwindClasses(it) }
            attrs?.invoke(this)
        },
        content
    )
}

@Composable
fun SpanTw(
    tailwindClasses: String?,
    attrs: AttrBuilderContext<HTMLSpanElement>? = null,
    content: ContentBuilder<HTMLSpanElement>? = null
) {
    Span(
        attrs = {
            tailwindClasses?.let { tailwindClasses(it) }
            attrs?.invoke(this)
        },
        content = content
    )
}

@Composable
fun ATw(
    tailwindClasses: String? = null,
    href: String? = null,
    attrs: AttrBuilderContext<HTMLAnchorElement>? = null,
    content: ContentBuilder<HTMLAnchorElement>? = null
) {
    A(
        href = href,
        attrs = {
            tailwindClasses?.let { tailwindClasses(it) }
            attrs?.invoke(this)
        },
        content = content
    )
}