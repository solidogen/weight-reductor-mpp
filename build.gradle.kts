buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha09")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}")
        classpath("com.google.gms:google-services:4.3.5")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.5.1")
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.7.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
        maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers/")
        maven(url = "https://jitpack.io")
    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                // todo useIR = true, also cleanup those declaration between modules
                jvmTarget = "1.8"
            }
        }
    }
}

// todo new syntax like in backend build.gradle

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