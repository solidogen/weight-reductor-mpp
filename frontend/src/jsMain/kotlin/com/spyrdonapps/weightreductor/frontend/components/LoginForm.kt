package com.spyrdonapps.weightreductor.frontend.components

import androidx.compose.runtime.Composable
import com.spyrdonapps.weightreductor.frontend.utils.tailwindClasses
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.*

private const val usernameLabelId = "username"
private const val passwordLabelId = "password"

@Composable
fun LoginForm() {
    Div(attrs = { tailwindClasses("bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col") }) {
        Div(attrs = { tailwindClasses("mb-4") }) {
            Label(
                forId = usernameLabelId,
                attrs = { tailwindClasses("block text-grey-darker text-sm font-bold mb-2") }
            ) {
                Text("Username")
            }
            Input(
                type = InputType.Text,
                attrs = {
                    tailwindClasses("shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker")
                    id(usernameLabelId)
                    placeholder("Username")
                }
            )
        }
        Div(attrs = { tailwindClasses("mb-6") }) {
            Label(
                forId = passwordLabelId,
                attrs = { tailwindClasses("block text-grey-darker text-sm font-bold mb-2") }
            // todo - red border class if empty f.e. for testing. extract btw and hoist state
            ) {
                Text("Password")
            }
            Input(
                type = InputType.Password,
                attrs = {
                    tailwindClasses("shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3")
                    id(passwordLabelId)
                    placeholder("**********")
                }
            )
            P(attrs = { tailwindClasses("text-red text-xs italic") }) {
                Text("Please choose a password")
            }
        }
        // todo finish https://tailwindcomponents.com/component/login-form
        //  todo extract composable
        //  todo fix colors
    }
}

//@Composable
//fun InputLabel(forId: String, placeholder: String, text: String, containerTailwindClasses: String, inputType) {
//    Div(attrs = { tailwindClasses(containerTailwindClasses) }) {
//        Label(
//            forId = forId,
//            attrs = { tailwindClasses("block text-grey-darker text-sm font-bold mb-2") }
//        ) {
//            Text("Username")
//        }
//        Input(
//            type = InputType.Text,
//            attrs = {
//                tailwindClasses("shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"),
//                id(usernameLabelId)
//                placeholder("Username")
//            }
//        )
//    }
//}