package com.spyrdonapps.common.model

sealed class Environment(val baseUrl: String) {

    object Local : Environment(local)
    object LocalAndroid : Environment(localAndroidEmulator)
    object Dev : Environment(dev)
    object Prod : Environment(prod)

    // todo generate from .properties file (duplicate)
    companion object {
        const val local = "http://0.0.0.0:9090"
        const val localAndroidEmulator = "http://10.0.2.2:9090"
        const val dev = "https://dev-wr.herokuapp.com"
        const val prod = "https://weightreductor.herokuapp.com"
    }
}