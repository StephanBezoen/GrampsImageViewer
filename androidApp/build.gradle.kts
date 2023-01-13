@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.8.0"
    id("org.sonarqube") version "3.3"
}

android {
    val localProperties = gradleLocalProperties(rootDir)

    namespace = "nl.acidcats.imageviewer.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "nl.acidcats.imageviewer.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // command line properties
        val propScheme: String by project
        val propHost: String by project
        val propJsonPath: String by project
        val propAssetsPath: String by project

        // local properties
        val apiScheme: String? = localProperties.getProperty("apiScheme")
        val apiHost: String? = localProperties.getProperty("apiHost")
        val apiPathJson: String? = localProperties.getProperty("apiPathJson")
        val apiPathAssets: String? = localProperties.getProperty("apiPathAssets")

        buildConfigField("String", "API_SCHEME", apiScheme ?: "\"$propScheme\"")
        buildConfigField("String", "API_HOST", apiHost ?: "\"$propHost\"")
        buildConfigField("String", "API_PATH_JSON", apiPathJson ?: "\"$propJsonPath\"")
        buildConfigField("String", "API_PATH_ASSETS", apiPathAssets ?: "\"$propAssetsPath\"")
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
            // command line properties
            val propKeystoreFile: String by project
            val propKeystorePassword: String by project
            val propKeystoreKey: String by project
            val propKeyPassword: String by project

            // local properties
            val keystoreFile: String? = localProperties.getProperty("keystore_file")
            val passwordForStore: String? = localProperties.getProperty("keystore_password")
            val keystoreKey: String? = localProperties.getProperty("keystore_key")
            val passwordForKey: String? = localProperties.getProperty("key_password")

            storeFile = file(keystoreFile  ?: propKeystoreFile)
            storePassword = passwordForStore ?: propKeystorePassword
            keyAlias = keystoreKey ?: propKeystoreKey
            keyPassword = passwordForKey ?: propKeyPassword
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