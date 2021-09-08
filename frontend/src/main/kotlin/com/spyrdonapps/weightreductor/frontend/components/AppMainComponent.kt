package com.spyrdonapps.weightreductor.frontend.components

import com.spyrdonapps.weightreductor.frontend.components.playground.playground
import com.spyrdonapps.weightreductor.frontend.components.playground.weighingsList
import react.*

class AppMainComponent : RComponent<Props, State>() {

    override fun RBuilder.render() {
        mainHeader { }
        // todo rest of main layout
        weighingsList { }
        playground { }
    }
}

fun RBuilder.appMainComponent(handler: Props.() -> Unit) {
    return child(AppMainComponent::class) {
        this.attrs(handler)
    }
}