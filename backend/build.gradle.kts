import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import java.io.FileWriter

plugins {
    kotlin("jvm") // todo multiplatform + jvmMain
    application
    kotlin("plugin.serialization")
}

dependencies {
    // todo - this imports only androidMain in IDE, rest is considered errors (although gradle build works fine)
    implementation(project(":common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")

    implementation(Deps.datetime)

    implementation("io.ktor:ktor-server-core:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization:${Versions.ktor}")
    implementation("io.ktor:ktor-auth:${Versions.ktor}")
    implementation("io.ktor:ktor-auth-jwt:${Versions.ktor}")
    implementation("io.ktor:ktor-locations:${Versions.ktor}")

    implementation(Koin.ktor)
    implementation(Koin.slf4j)

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}") // JVM dependency
    implementation("io.ktor:ktor-websockets:${Versions.ktor}")

    implementation("org.postgresql:postgresql:${Versions.postgres}")
    implementation("com.zaxxer:HikariCP:4.0.2")
    implementation ("com.h2database:h2:1.4.200")
    implementation("org.flywaydb:flyway-core:7.5.3")
    implementation("com.viartemev:ktor-flyway-feature:1.3.0")
    implementation("at.favre.lib:bcrypt:0.9.0")
    implementation("com.auth0:java-jwt:3.18.1")

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
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn")
        }
    }
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
                    // Create this file locally if doesn't exist (only local builds)
                    // CI should be configured to create this file itself by running setAsCiBuild.sh script
                    setProperty(isCiBuildKey, "false")
                    val writer = FileWriter(inputs.files.singleFile, false)
                    store(writer, "CI variables can be changed by generating a ci-variables.properties file with generateCiVariables gradle task")
                }
                inputs.files.single().inputStream().use { load(it) }
            }
            File(outputDir, "CiVariables.kt").printWriter().use { writer ->
                writer.println("package com.spyrdonapps.weightreductor.backend")
                writer.println()
                writer.println("// Generated by generateCiVariables gradle task")
                writer.println("object CiVariables {")
                val ciVariablesToWrite = properties.stringPropertyNames().sorted()
                for (name in ciVariablesToWrite) {
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