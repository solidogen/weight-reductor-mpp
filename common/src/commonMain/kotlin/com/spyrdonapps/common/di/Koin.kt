package com.spyrdonapps.common.di

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Environment
import com.spyrdonapps.common.repository.ClientRepository
import com.spyrdonapps.common.getLogger
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    enableNetworkLogs: Boolean = false,
    environment: Environment,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    modules(
        commonModule(
            enableNetworkLogs = enableNetworkLogs,
            environment = environment
        )
    )
    appDeclaration()
}

fun commonModule(enableNetworkLogs: Boolean, environment: Environment) = module {
    single { environment }
    single { createJson() }
    single { Kermit(getLogger()) }
    single { createHttpClient(json = get(), enableNetworkLogs = enableNetworkLogs, kermit = get()) }
    single { ClientRepository(client = get(), environment = get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(json: Json, enableNetworkLogs: Boolean, kermit: Kermit) = HttpClient {
    install(JsonFeature) {
        acceptContentTypes = listOf(
            ContentType.parse("application/json")
        )
        serializer = KotlinxSerializer(json)
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
    // todo - install some refresh token logic. no idea what I'm doing here
//    install(Auth) {
//        bearer {
//            refreshTokens {
//
//            }
//        }
//    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    kermit.d { message }
                }
            }
            level = LogLevel.ALL
        }
    }
}