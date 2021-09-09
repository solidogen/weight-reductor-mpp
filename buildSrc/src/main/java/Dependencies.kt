object Versions {
    // todo move ALL versions from all modules here
    // todo ben manes plugin

    const val kotlin = "1.5.21"
    const val kotlinCoroutines = "1.5.1-native-mt"
    const val ktor = "1.6.2"
    const val kotlinxSerialization = "1.2.2"
    const val koin = "3.1.2"
    const val sqlDelight = "1.5.0"
    const val kermit = "0.1.9"

    const val exposed = "0.33.1"
    const val postgres = "42.2.2"

    const val lifecycle = "2.3.0"

    const val sqliteJdbcDriver = "3.30.1"
    const val slf4j = "1.7.30"

    const val compose = "1.0.2"
    const val wearCompose = "1.0.0-alpha03"
    const val navCompose = "2.4.0-alpha06"
    const val accompanist = "0.17.0"
    const val coil = "1.3.2"

    const val junit = "4.13.2"
    const val testRunner = "1.3.0"

    const val datetime = "0.2.1"
}

object AndroidSdk {
    const val min = 23
    const val compile = 30
    const val target = 30
}

enum class RawEnvironment {
    Local,
    LocalAndroidEmulator,
    Dev,
    Prod
}

object Deps {
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.datetime}"
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navCompose}"
//    const val accompanist = "com.google.accompanist:accompanist:${Versions.accompanist}" // this was split into smaller parts
    const val composeCoil = "io.coil-kt:coil-compose:${Versions.coil}"
}

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val test = "io.insert-koin:koin-test:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    const val ktor = "io.insert-koin:koin-ktor:${Versions.koin}"
    const val slf4j = "io.insert-koin:koin-logger-slf4j:${Versions.koin}"
}

object Ktor {
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
    const val clientAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"
    const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"

    const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val clientApache = "io.ktor:ktor-client-apache:${Versions.ktor}"
    const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
    const val clientCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    const val clientJs = "io.ktor:ktor-client-js:${Versions.ktor}"
}

object Serialization {
    const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
    const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
}

object SqlDelight {
    const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
    const val coroutineExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    const val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"

    const val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
    const val nativeDriverMacos = "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
    const val jdbcDriver = "org.xerial:sqlite-jdbc:${Versions.sqliteJdbcDriver}"
    const val sqlliteDriver = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
}

