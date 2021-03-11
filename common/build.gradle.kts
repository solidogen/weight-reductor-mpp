import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import java.util.Properties

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("com.codingfeline.buildkonfig")
}

/**
 * Imitating Android's BuildConfig for other clients.
 * Environment can be changed by running local deploy scripts or by CI
 * */
buildkonfig {
    packageName = "com.spyrdonapps.weightreductor"
    exposeObjectWithName = "JsBuildConfig"

    val defaultEnvironment = "${RawEnvironment.Local}"
    val environmentTag = "raw_environment"
    val properties = Properties().apply {
        file("../environment.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
    }
    val overriddenEnvironment: String? = properties.getProperty(environmentTag)
    defaultConfigs {
        buildConfigField(Type.STRING, "RAW_ENVIRONMENT", overriddenEnvironment ?: defaultEnvironment)
    }
}

android {
    compileSdkVersion(AndroidSdk.compile)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // workaround for https://youtrack.jetbrains.com/issue/KT-43944
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    android()
    jvm()
    js {
        browser {
        }
    }

    targets.all {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = true
                freeCompilerArgs = listOf(
                    "-Xopt-in=kotlin.RequiresOptIn"
                )
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}") {
                    isForce = true
                }

                // Ktor
                implementation(Ktor.clientCore)
                implementation(Ktor.clientJson)
                implementation(Ktor.clientLogging)
                implementation(Ktor.clientSerialization)

                // Kotlinx Serialization
                implementation(Serialization.core)
                implementation(Serialization.json)

                // Kotlinx Datetime
                implementation(Deps.datetime)

                // SQL Delight
                implementation(SqlDelight.runtime)
                implementation(SqlDelight.coroutineExtensions)

                // koin
                api(Koin.core)

                // kermit
                api(Deps.kermit)
            }
        }
        val commonTest by getting {}
        val androidMain by getting {
            dependencies {
                implementation(Ktor.clientAndroid)
                implementation(SqlDelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(Test.junit)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Ktor.clientApache)
                implementation(Ktor.slf4j)
                implementation(SqlDelight.jdbcDriver)
                implementation(SqlDelight.sqlliteDriver)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(Ktor.clientJs)
            }
        }
    }
}