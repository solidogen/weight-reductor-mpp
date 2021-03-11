import java.util.Properties
import java.io.FileWriter

plugins {
    id("kotlin-platform-jvm")
    application
    kotlin("plugin.serialization")
}

dependencies {
    // todo - I'm not sure about this. I don't think I need anything from common module in backend,
    //  I could share dto classes in plain gradle module instead - this way AGP doesn't break because of JVM sharing (probably)
    // todo - remove this and see if anything changed
    implementation(project(":common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")

    implementation(Deps.datetime)

    implementation("io.ktor:ktor-server-core:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization:${Versions.ktor}")
    implementation("io.ktor:ktor-auth:${Versions.ktor}")
    implementation("io.ktor:ktor-locations:${Versions.ktor}")

    implementation(Koin.ktor)
    implementation(Koin.slf4j)

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}") // JVM dependency
    implementation("io.ktor:ktor-websockets:${Versions.ktor}")

    implementation("org.postgresql:postgresql:${Versions.postgres}")
    implementation("com.zaxxer:HikariCP:4.0.2")
    implementation("org.flywaydb:flyway-core:7.5.3")
    implementation("com.viartemev:ktor-flyway-feature:1.2.2")

    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-java-time:${Versions.exposed}")

    testImplementation(Koin.test)
    testImplementation("io.ktor:ktor-server-test-host:${Versions.ktor}")
}

application {
    mainClass.set("com.spyrdonapps.weightreductor.backend.BackendAppKt")
}

val ciVariablesPath = "build/generated/source/ci"

kotlin {
    sourceSets["main"].kotlin.srcDir(ciVariablesPath)
}

tasks {
    val generateCiVariables by registering {
        val isCiBuildKey = "IS_CI_BUILD"
        group = "codegen"
        inputs.files("../ci-variables.properties")
        outputs.dir(ciVariablesPath)
        outputs.upToDateWhen { false }
        doLast {
            val outputDir = outputs.files.single()
            val properties = Properties().apply {
                if (!inputs.files.singleFile.exists()) {
                    /**
                    * Create this file if doesn't exist (non-CI behavior, CI should create this file itself and set needed variables)
                    * */
                    setProperty(isCiBuildKey, "false")
                    val writer = FileWriter(inputs.files.singleFile, false)
                    store(writer, "CI variables can be changed by generating a ci-variables.properties file.")
                }
                inputs.files.single().inputStream().use { load(it) }
            }
            File(outputDir, "CiVariables.kt").printWriter().use { writer ->
                writer.println("package com.spyrdonapps.weightreductor.backend")
                writer.println("")
                writer.println("object CiVariables {")
                for (name in properties.stringPropertyNames().sorted().filter { it == isCiBuildKey }) {
                    writer.println("    const val $name = \"${properties.getProperty(name)}\"")
                }
                writer.println("}")
            }
        }
    }
    val compileKotlin by existing
    compileKotlin {
        dependsOn(generateCiVariables)
    }
}