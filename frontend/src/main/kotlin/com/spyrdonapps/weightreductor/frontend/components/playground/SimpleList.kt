package com.spyrdonapps.weightreductor.frontend.components.playground

import react.*
import react.dom.p

external interface SimpleListProps : RProps {
    var list: List<String>
}

class SimpleList : RComponent<SimpleListProps, RState>() {

    override fun RBuilder.render() {
        props.list.forEach { listItem ->
            p {
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