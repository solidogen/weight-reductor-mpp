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
//            binaries.executable()
        }
    }

    sourceSets {
        sourceSets["androidMain"].dependencies {

        }
    }
}