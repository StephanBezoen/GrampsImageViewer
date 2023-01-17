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
    versionCatalogs {
        create("libraries") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "ImageViewer"
include(":androidApp")
include(":shared")