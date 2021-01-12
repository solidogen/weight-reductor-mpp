package com.spyrdonapps.common.model

sealed class Environment(val baseUrl: String) {

    object Local : Environment(localUrl)
    object LocalAndroid : Environment(localAndroidUrl)
    object Dev : Environment(devUrl)
    object Prod : Environment(prodUrl)

    companion object {
        const val localUrl = "http://0.0.0.0:9090"
        const val localAndroidUrl = "http://10.0.2.2:9090"
        const val devUrl = "https://dev-wr.herokuapp.com"
        const val prodUrl = "https://weightreductor.herokuapp.com"
    }
}