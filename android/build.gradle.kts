plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = AndroidSdk.compile
    defaultConfig {
        applicationId = "com.spyrdonapps.weightreductor"
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = 1 // todo move to buildSrc
        versionName = "0.0.1" // todo move to buildSrc
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions("version")
    productFlavors {
        /**
         * TODO find a way to connect physical device to locally hosted backend (not super important, emulator works fine for now)
         * */
        create("local") {
            applicationIdSuffix = ".local"
            versionNameSuffix = "-local"
            resValue("string", "app_name", "WR Local")
            buildConfigField(
                "String",
                "RAW_ENVIRONMENT",
                "\"${RawEnvironment.LocalAndroidEmulator}\""
            )
        }
        create("dev") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "WR Dev")
            buildConfigField("String", "RAW_ENVIRONMENT", "\"${RawEnvironment.Dev}\"")
        }
        create("prod") {
            resValue("string", "app_name", "Weight Reductor")
            buildConfigField("String", "RAW_ENVIRONMENT", "\"${RawEnvironment.Prod}\"")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
}

dependencies {
    implementation(project(":common"))

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("androidx.activity:activity-compose:1.3.1")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}")

    implementation(platform("com.google.firebase:firebase-bom:26.6.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation(Compose.ui)
    implementation(Compose.uiGraphics)
    implementation(Compose.uiTooling)
    implementation(Compose.foundationLayout)
    implementation(Compose.material)
    implementation(Compose.runtimeLiveData)
    implementation(Compose.navigation)
    implementation(Compose.composeCoil)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.compose)

    implementation(Deps.datetime)

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    testImplementation(Test.junit)
    androidTestImplementation("androidx.test:runner:${Versions.testRunner}")
}