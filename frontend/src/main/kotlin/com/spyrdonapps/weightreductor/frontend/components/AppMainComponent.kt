package com.spyrdonapps.weightreductor.frontend.components

import com.spyrdonapps.weightreductor.frontend.components.playground.weighingsList
import react.*

class AppMainComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        mainHeader { }
        // todo rest of main layout
        weighingsList { }
//        playground { }
    }
}

fun RBuilder.appMainComponent(handler: RProps.() -> Unit): ReactElement {
    return child(AppMainComponent::class) {
        this.attrs(handler)
    }
}