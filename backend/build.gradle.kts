import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("kotlin-platform-jvm")
    application
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
}

dependencies {
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
    implementation("com.h2database:h2:1.4.200")
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("org.flywaydb:flyway-core:6.5.5")

    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
//    implementation("org.jetbrains.exposed:exposed-java-time:${Versions.exposed}") // todo kotlinx-datetime somehow

    testImplementation(Koin.test)
    testImplementation("io.ktor:ktor-server-test-host:${Versions.ktor}")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("wr.jar")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
    }
}
