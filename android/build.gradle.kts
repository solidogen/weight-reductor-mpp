plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.spyrdonapps.weightreductor"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)

        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "LOCAL_BACKEND_IP", "\"${getLocalBackendIp()}\"")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.4.21"
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {
    implementation(project(":common"))

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.foundationLayout)
    implementation(Compose.material)
    implementation(Compose.runtimeLiveData)
    implementation(Compose.navigation)
    implementation(Compose.accompanist)

    implementation(Koin.android)
    implementation(Koin.androidViewModel)

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test:runner:1.2.0")
}

fun getLocalBackendIp(): String {
//        val defaultDevHost = InetAddress.getLocalHost()
//            .getCanonicalHostName()

//        return NetworkInterface.getNetworkInterfaces()
//            .findResult(defaultDevHost) {
//                def ifOk = it.isUp() &&
//                        !it.isVirtual() &&
//                        !it.isPointToPoint() &&
//                        !it.isLoopback() &&
//                        !it.getName().startsWith("br-") //
//
//                if (ifOk)
//                    return it.getInetAddresses().find {
//                        it instanceof Inet4Address &&
//                                !it.isLoopbackAddress()
//                    }
//                else
//                    return null
//            }
    return "local-ip-todo"
}