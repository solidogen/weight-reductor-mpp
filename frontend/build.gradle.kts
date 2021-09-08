plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

val kotlinWrappersVersion = "0.0.1-pre.243-kotlin-1.5.30"

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(Deps.datetime)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")

    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:${kotlinWrappersVersion}"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css")

    implementation(project(":common"))
}


kotlin {
    js {
        useCommonJs()
        browser()
        binaries.executable()
    }
}

// todo - remove this when kotlin can be bumped to 1.5.30 (blocked by jetpack compose)
afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
    }
}