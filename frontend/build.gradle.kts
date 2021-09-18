import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.0-alpha4-build348"
//    kotlin("plugin.serialization") // TODO - is this needed here?
}

version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.widgets)
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(Deps.datetime)
                implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:1.0.1-pre.243-kotlin-1.5.30")
                implementation(npm("postcss", Versions.postcss))
                implementation(npm("postcss-loader", Versions.postcssLoader))
                implementation(npm("autoprefixer", Versions.autoprefixer))
                implementation(npm("tailwindcss", Versions.tailwind))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")
                implementation(project(":common"))
            }
        }
    }
}

// todo - remove this when kotlin can be bumped to 1.5.30 (blocked by jetpack compose)
afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
    }
}

compose.desktop {
    application {
        mainClass = ""
    }
}

val copyTailwindConfig = tasks.register<Copy>("copyTailwindConfig") {
    from("./tailwind.config.js")
    into("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")

    dependsOn(":kotlinNpmInstall")
}

val copyPostcssConfig = tasks.register<Copy>("copyPostcssConfig") {
    from("./postcss.config.js")
    into("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")

    dependsOn(":kotlinNpmInstall")
}

tasks.named("compileKotlinJs") {
    dependsOn(copyTailwindConfig)
    dependsOn(copyPostcssConfig)
}