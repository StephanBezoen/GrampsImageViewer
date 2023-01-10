@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.8.0"
}

val apiScheme: String = gradleLocalProperties(rootDir).getProperty("apiScheme")
val apiHost: String = gradleLocalProperties(rootDir).getProperty("apiHost")
val apiPathJson: String = gradleLocalProperties(rootDir).getProperty("apiPathJson")
val apiPathAssets: String = gradleLocalProperties(rootDir).getProperty("apiPathAssets")

android {
    namespace = "nl.acidcats.imageviewer.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "nl.acidcats.imageviewer.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_SCHEME", apiScheme)
        buildConfigField("String", "API_HOST", apiHost)
        buildConfigField("String", "API_PATH_JSON", apiPathJson)
        buildConfigField("String", "API_PATH_ASSETS", apiPathAssets)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            val keystoreFile: String = gradleLocalProperties(rootDir).getProperty("keystore_file")
            val passwordForStore: String = gradleLocalProperties(rootDir).getProperty("keystore_password")
            val keystoreKey: String = gradleLocalProperties(rootDir).getProperty("keystore_key")
            val passwordForKey: String = gradleLocalProperties(rootDir).getProperty("key_password")

            storeFile = file(keystoreFile)
            storePassword = passwordForStore
            keyAlias = keystoreKey
            keyPassword = passwordForKey
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.0"
        )
    }
}

dependencies {
    val composeVersion = "1.3.2"
    val lifeyceVersion = "2.5.1"
    val coilVersion = "2.2.2"

    implementation(project(":shared"))

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    implementation("io.insert-koin:koin-core:3.3.2")
    implementation("io.insert-koin:koin-android:3.3.2")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifeyceVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifeyceVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeyceVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeyceVersion")

    implementation("io.github.aakira:napier:2.6.1")

    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")

    implementation("com.google.android.exoplayer:exoplayer:2.18.2")
    implementation("androidx.profileinstaller:profileinstaller:1.3.0-alpha02")
}