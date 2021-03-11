package com.spyrdonapps.common.model

import com.spyrdonapps.common.util.extensions.valueForNameOrNull
import com.spyrdonapps.common.util.utils.ClientType

const val localUrl = "http://0.0.0.0:9090"
const val localAndroidEmulatorUrl = "http://10.0.2.2:9090"
const val devUrl = "https://dev-wr.herokuapp.com"
const val prodUrl = "https://weightreductor.herokuapp.com"

enum class Environment(val baseUrl: String) {
    Local(localUrl),
    LocalAndroidEmulator(localAndroidEmulatorUrl),
    Dev(devUrl),
    Prod(prodUrl);
    
    fun ensureAvailableForClientType(clientType: ClientType) {
        if (clientType == ClientType.Javascript && this == LocalAndroidEmulator) {
            error("LocalAndroidEmulator environment not available for frontend client")
        }
        if (clientType == ClientType.Android && this == Local) {
            error("Local backend access is currently not supported on physical Android device, " +
                    "use LocalAndroidEmulator environment on emulator instead for now")
        }
    }

    companion object {
        fun fromRawEnvironment(rawEnvironment: String): Environment =
            valueForNameOrNull<Environment>(rawEnvironment)
                ?: error("No Environment enum for raw enum string: $rawEnvironment")
    }
}