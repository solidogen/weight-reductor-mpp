package com.spyrdonapps.weightreductor.frontend.components

import com.spyrdonapps.weightreductor.frontend.components.playground.playground
import react.*

class AppMainComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        mainHeader {  }
        // todo rest of main layout
        playground {}
    }
}

fun RBuilder.appMainComponent(handler: RProps.() -> Unit): ReactElement {
    return child(AppMainComponent::class) {
        this.attrs(handler)
    }
}