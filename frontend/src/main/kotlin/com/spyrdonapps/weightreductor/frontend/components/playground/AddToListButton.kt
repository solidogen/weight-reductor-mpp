package com.spyrdonapps.weightreductor.frontend.components.playground

import co.touchlab.kermit.Kermit
import com.spyrdonapps.weightreductor.frontend.AppDependenciesContext
import com.spyrdonapps.weightreductor.frontend.resources.CustomColor
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.color
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.form
import react.dom.input
import react.dom.label
import styled.css
import styled.styledButton

external interface AddToListFormProps : RProps {
    var addToList: (String) -> Unit
}

external interface AddToListFormState : RState {
    var inputText: String
}

class AddToListForm : RComponent<AddToListFormProps, AddToListFormState>() {

    override fun AddToListFormState.init() {
        inputText = ""
    }

    override fun RBuilder.render() {
        var logger: Kermit? = null
        AppDependenciesContext.Consumer { dependencies ->
            logger = dependencies.logger
        }

        form {
            label {
                attrs["htmlFor"] = ITEM_INPUT_ID
                +"Enter item name"
            }
            input {
                attrs.apply {
                    value = state.inputText
                    placeholder = "Item name"
                    type = InputType.text
                    id = ITEM_INPUT_ID
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        logger?.d { target.value }
                        setState {
                            inputText = target.value
                        }
                    }
                }
            }
            styledButton {
                css {
                    backgroundColor = CustomColor.green
                    color = Color.white
                }
                attrs.apply {
                    type = ButtonType.submit
                    onClickFunction = {
                        it.preventDefault()
                        props.addToList.invoke(state.inputText)
                        setState {
                            inputText = ""
                        }
                    }
                }
                +"Add to list"
            }
        }
    }

    companion object {
        const val ITEM_INPUT_ID = "ITEM_INPUT_ID"
    }
}

fun RBuilder.addToListForm(handler: AddToListFormProps.() -> Unit): ReactElement {
    return child(AddToListForm::class) {
        this.attrs(handler)
    }
}