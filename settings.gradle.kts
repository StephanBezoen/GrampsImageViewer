@file:Suppress("UnstableApiUsage")

include(":benchmark")


pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
//        classpath("com.vanniktech:gradle-dependency-graph-generator-plugin:0.8.0")
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