pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}


rootProject.name = "weight-reductor-mpp"

enableFeaturePreview("GRADLE_METADATA")

//include(":")
include(":common")
