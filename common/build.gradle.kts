plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

android {
    compileSdkVersion(AndroidSdk.compile)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

kotlin {
    android()
    jvm()
    js {
        browser {
        }
    }

    sourceSets {
        sourceSets["commonMain"].dependencies {
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

            // SQL Delight
            implementation(SqlDelight.runtime)
            implementation(SqlDelight.coroutineExtensions)

            // koin
            api(Koin.core)

            // kermit
            api(Deps.kermit)
        }
        sourceSets["commonTest"].dependencies {
        }
        sourceSets["androidMain"].dependencies {
            implementation(Ktor.clientAndroid)
            implementation(SqlDelight.androidDriver)
        }
        sourceSets["androidTest"].dependencies {
            implementation(kotlin("test-junit"))
            implementation(Test.junit)
        }
        sourceSets["jvmMain"].dependencies {
            implementation(Ktor.clientApache)
            implementation(Ktor.slf4j)
            implementation(SqlDelight.jdbcDriver)
            implementation(SqlDelight.sqlliteDriver)
        }
        sourceSets["jsMain"].dependencies {
            implementation(Ktor.clientJs)
        }
    }
}