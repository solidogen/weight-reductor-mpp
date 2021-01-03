package com.spyrdonapps.weightreductor.frontend.components.playground

import react.*
import react.dom.h1

external interface PlaygroundState : RState {
    var list: List<String>
}

class Playground : RComponent<RProps, PlaygroundState>() {

    override fun PlaygroundState.init() {
        list = listOf("simple 1", "simple 2")
    }

    override fun RBuilder.render() {
        h1 {
            +"Playground"
        }
        simpleList {
            list = state.list
        }
        addToListForm {
            addToList = { newItem ->
                setState {
                    list += newItem
                }
            }
        }
    }
}

fun RBuilder.playground(handler: RProps.() -> Unit): ReactElement {
    return child(Playground::class) {
        this.attrs(handler)
    }
}