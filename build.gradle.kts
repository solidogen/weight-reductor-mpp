import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.osacky.doctor.DoctorExtension

plugins {
    id("com.github.ben-manes.versions") version "0.39.0" // run scripts/checkForDependencyUpdates.sh to list all available updates
    id("com.osacky.doctor") version "0.7.0" // gives hints how to speed-up builds
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.7.0")
        classpath("app.cash.licensee:licensee-gradle-plugin:1.2.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

configure<DoctorExtension> {
    javaHome {
        failOnError.set(false)
    }
}

tasks.withType<DependencyUpdatesTask> {
    // show only stable version of gradle
    gradleReleaseChannel = "current"
    rejectVersionIf {
        // show only stable versions
        isNonStable(candidate.version) &&
                // hide warnings for current unstable versions
                !isNonStable(currentVersion)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// Alias "installDist" as "stage" for Heroku, to prepare jar for Procfile access
tasks.create("stage") {
    dependsOn(":backend:installDist")
}

tasks {
    create<Copy>("installGitHook") {
        group = "git"
        from(file("${rootProject.rootDir}/scripts/git/pre-commit"))
        into(file("${rootProject.rootDir}/.git/hooks"))
        fileMode = 0b111101101
    }
    val installGitHook by existing
    val dependedTask = getByPath(":backend:compileKotlin")
    dependedTask.dependsOn(installGitHook)
}