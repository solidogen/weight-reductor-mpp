package com.spyrdonapps.weightreductor.frontend.components

import androidx.compose.runtime.*
import com.spyrdonapps.common.util.utils.Action
import com.spyrdonapps.weightreductor.frontend.utils.setEnabled
import com.spyrdonapps.weightreductor.frontend.utils.tailwindClasses
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*

@Composable
fun AppButton(text: String, isEnabled: Boolean, onClick: Action) {
    Button(attrs = {
        tailwindClasses("bg-blue-600 hover:bg-blue-900 active:bg-blue-300 disabled:bg-gray-200 text-white disabled:text-gray-500 font-bold py-2 px-4 rounded")
        type(ButtonType.Button)
        onClick { onClick.invoke() }
        setEnabled(isEnabled)
    }) {
        Text(text)
    }
}