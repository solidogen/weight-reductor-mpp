package com.spyrdonapps.weightreductor.frontend.utils

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.Element
import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGPathElement

/**
 * This exists only because compose-web doesn't support this yet.
 * Author: Martynas Petuška
 * */

private val Svg: ElementBuilder<SVGElement> = object : ElementBuilder<SVGElement> {
    private val el: Element by lazy {
        document.createElementNS(
            "http://www.w3.org/2000/svg",
            "svg"
        )
    }

    override fun create(): SVGElement = el.cloneNode() as SVGElement
}

private val Path: ElementBuilder<SVGPathElement> = object : ElementBuilder<SVGPathElement> {
    private val el: Element by lazy {
        document.createElementNS(
            "http://www.w3.org/2000/svg",
            "path"
        )
    }

    override fun create(): SVGPathElement = el.cloneNode() as SVGPathElement
}

@Composable
public fun Svg(
    attrs: AttrBuilderContext<SVGElement>? = null,
    content: ContentBuilder<SVGElement>? = null
) {
    TagElement(
        elementBuilder = Svg,
        applyAttrs = attrs,
        content = content
    )
}

@Composable
public fun ElementScope<SVGElement>.Path(
    attrs: AttrBuilderContext<SVGPathElement>? = null,
    content: ContentBuilder<SVGPathElement>? = null
) {
    TagElement(
        elementBuilder = Path,
        applyAttrs = attrs,
        content = content
    )
}