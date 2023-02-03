@file:Suppress("UnstableApiUsage", "UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("project-report")
    id("com.vanniktech.dependency.graph.generator")
}

kotlin {
    android()
    ios()

    listOf(
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "2.2.2"

        val commonMain by getting {
            dependencies {
                implementation(libraries.ktor.client.core)
                implementation(libraries.ktor.client.logging)
                implementation(libraries.ktor.client.contentnegotiation)
                implementation(libraries.ktor.serialization.json)

                implementation(libraries.koin.core)

                implementation(libraries.napier)

                implementation(libraries.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
                implementation("app.cash.turbine:turbine:0.12.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libraries.ktor.client.okhttp)
                implementation(libraries.ktor.client.core)
            }
        }
        val androidUnitTest by getting
        val iosMain by getting {
            dependencies {
                implementation(libraries.ktor.client.darwin)
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    namespace = "nl.acidcats.imageviewer"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
        @Suppress("DEPRECATION")
        targetSdk = 33
    }
}