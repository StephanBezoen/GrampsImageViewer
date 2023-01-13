@file:Suppress("UnstableApiUsage", "UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.20"
    id("com.android.library")
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
        val koinVersion = "3.3.2"

        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation("io.insert-koin:koin-core:$koinVersion")

                implementation("io.github.aakira:napier:2.6.1")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
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
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val androidUnitTest by getting
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
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