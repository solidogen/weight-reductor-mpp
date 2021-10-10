package com.spyrdonapps.weightreductor.android.ui.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spyrdonapps.common.model.UserCredentials
import com.spyrdonapps.common.util.utils.Action

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    goToRegisterScreen: Action,
    loginRequested: (UserCredentials) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.Center)
                .padding(32.dp)
        ) {

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val keyboardController = LocalSoftwareKeyboardController.current

            Text(
                text = "Login",
                fontSize = 30.sp,
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                onClick = {
                    keyboardController?.hide()
                    loginRequested.invoke(UserCredentials(username = username, password = password))
                }
            ) {
                Text(text = "Login")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                onClick = goToRegisterScreen
            ) {
                Text(text = "Don't have the account?")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(goToRegisterScreen = {}, loginRequested = {})
}