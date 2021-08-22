pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}


rootProject.name = "weight-reductor-mpp"

include(":common")
include(":android")
include(":backend")
include(":frontend")
