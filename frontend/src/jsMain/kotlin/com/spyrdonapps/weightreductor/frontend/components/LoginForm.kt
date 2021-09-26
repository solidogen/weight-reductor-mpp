package com.spyrdonapps.weightreductor.frontend.components

import androidx.compose.runtime.*
import com.spyrdonapps.common.model.UserCredentials
import com.spyrdonapps.weightreductor.frontend.utils.setEnabled
import com.spyrdonapps.weightreductor.frontend.utils.tailwindClasses
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*

private const val usernameLabelId = "username"
private const val passwordLabelId = "password"

@Composable
fun LoginForm(onLoginButtonClick: (UserCredentials) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isUsernameValid = username.isNotBlank()
    val isPasswordValid = password.isNotBlank()
    val isLoginButtonEnabled = isUsernameValid && isPasswordValid

    Div(attrs = { tailwindClasses("bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 flex flex-col") }) {
        Div(attrs = { tailwindClasses("mb-4") }) {
            Label(
                forId = usernameLabelId,
                attrs = { tailwindClasses("block text-gray-600 text-sm font-bold mb-2") }
            ) {
                Text("Username")
            }
            Input(
                type = InputType.Text,
                attrs = {
                    tailwindClasses("shadow appearance-none border focus:outline-none focus:ring focus:ring-blue-600 rounded w-full py-2 px-3 text-gray-600")
                    if (!isUsernameValid) {
                        tailwindClasses("border-red-600")
                    }
                    id(usernameLabelId)
                    placeholder("Username")
                    onInput { username = it.value }
                    onChange { username = it.value }
                }
            )
        }
        Div(attrs = { tailwindClasses("mb-6") }) {
            Label(
                forId = passwordLabelId,
                attrs = { tailwindClasses("block text-gray-600 text-sm font-bold mb-2") }
            ) {
                Text("Password")
            }
            Input(
                type = InputType.Password,
                attrs = {
                    tailwindClasses("shadow appearance-none border focus:outline-none focus:ring focus:ring-blue-600 rounded w-full py-2 px-3 text-gray-600 mb-3")
                    if (!isPasswordValid) {
                        tailwindClasses("border-red-600")
                    }
                    id(passwordLabelId)
                    placeholder("**********")
                    onInput { password = it.value }
                    onChange { password = it.value }
                }
            )
            if (!isPasswordValid) {
                P(attrs = { tailwindClasses("text-red-600 text-xs italic") }) {
                    Text("Please input a password")
                }
            }
        }
        Div(attrs = { tailwindClasses("flex items-center justify-between") }) {
            AppButton(
                text = "Login",
                isEnabled = isLoginButtonEnabled,
                onClick = { onLoginButtonClick.invoke(UserCredentials(username = username, password = password)) }
            )
            A(attrs = { tailwindClasses("inline-block align-baseline font-bold text-sm text-blue-600 hover:text-blue-900") }) {
                Text("Forgot Password?")
            }
        }
    }
}

@Composable
fun AppButton(text: String, isEnabled: Boolean, onClick: () -> Unit) {
    Button(attrs = {
        tailwindClasses("bg-blue-600 hover:bg-blue-900 active:bg-blue-300 disabled:bg-gray-200 text-white disabled:text-gray-500 font-bold py-2 px-4 rounded")
        type(ButtonType.Button)
        onClick { onClick.invoke() }
        setEnabled(isEnabled)
    }) {
        Text(text)
    }
}

//@Composable
//fun InputLabel(forId: String, placeholder: String, text: String, containerTailwindClasses: String, inputType) {
//    Div(attrs = { tailwindClasses(containerTailwindClasses) }) {
//        Label(
//            forId = forId,
//            attrs = { tailwindClasses("block text-gray-600 text-sm font-bold mb-2") }
//        ) {
//            Text("Username")
//        }
//        Input(
//            type = InputType.Text,
//            attrs = {
//                tailwindClasses("shadow appearance-none border rounded w-full py-2 px-3 text-gray-600"),
//                id(usernameLabelId)
//                placeholder("Username")
//            }
//        )
//    }
//}