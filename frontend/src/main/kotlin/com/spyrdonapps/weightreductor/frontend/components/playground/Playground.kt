package com.spyrdonapps.weightreductor.frontend.components.playground

import react.*
import react.dom.h1
import react.dom.h2

external interface PlaygroundState : State {
    var list: List<String>
}

class Playground : RComponent<Props, PlaygroundState>() {

    override fun PlaygroundState.init() {
        list = listOf("simple item 1", "simple item 2")
    }

    override fun RBuilder.render() {
        h1 {
            +"Playground"
        }
        h2 {
            +"User items:"
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

fun RBuilder.playground(handler: Props.() -> Unit) {
    return child(Playground::class) {
        this.attrs(handler)
    }
}