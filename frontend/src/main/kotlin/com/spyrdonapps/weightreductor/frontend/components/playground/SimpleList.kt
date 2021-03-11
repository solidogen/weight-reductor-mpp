package com.spyrdonapps.weightreductor.frontend.components.playground

import kotlinx.css.*
import kotlinx.css.properties.border
import react.*
import react.dom.p
import styled.css
import styled.styledP

external interface SimpleListProps : RProps {
    var list: List<String>
}

class SimpleList : RComponent<SimpleListProps, RState>() {

    override fun RBuilder.render() {
        props.list.forEach { listItem ->
            styledP {
                css {
                    // no idea how to use border in css
//                    border(width = LinearDimension.fillAvailable, style = BorderStyle.solid, color = Color.blueViolet)
                    backgroundColor = Color.azure
                }
                +listItem
            }
        }
    }
}

fun RBuilder.simpleList(handler: SimpleListProps.() -> Unit): ReactElement {
    return child(SimpleList::class) {
        this.attrs(handler)
    }
}