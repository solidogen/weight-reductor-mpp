package com.spyrdonapps.weightreductor.frontend.components

import com.spyrdonapps.weightreductor.frontend.resources.CustomColor
import kotlinx.css.*
import react.*
import styled.css
import styled.styledH1
import styled.styledHeader

class MainHeader : RComponent<Props, State>() {

    override fun RBuilder.render() {
        styledHeader {
            css {
                backgroundColor = CustomColor.green
            }
            styledH1 {
                css {
                    marginTop = 0.px
                    color = Color.white
                    textAlign = TextAlign.center
                    padding(10.px)
                }
                +"Weight Reductor"
            }
        }
    }
}

fun RBuilder.mainHeader(handler: Props.() -> Unit) {
    return child(MainHeader::class) {
        this.attrs(handler)
    }
}