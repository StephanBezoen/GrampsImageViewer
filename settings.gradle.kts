@file:Suppress("UnstableApiUsage")

include(":benchmark")


pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ImageViewer"
include(":androidApp")
include(":shared")