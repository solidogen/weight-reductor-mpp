package com.spyrdonapps.weightreductor.frontend.components

import react.*

class AppMainComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        mainHeader {  }
        // todo rest of main layout
    }
}

fun RBuilder.appMainComponent(handler: RProps.() -> Unit): ReactElement {
    return child(AppMainComponent::class) {
        this.attrs(handler)
    }
}