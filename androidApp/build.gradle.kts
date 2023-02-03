@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    kotlin("android")
    id("com.android.application")
    kotlin("plugin.serialization")
    id("project-report")
    id("com.vanniktech.dependency.graph.generator")
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
    implementation(project(":shared"))

    implementation(libraries.compose.ui)
    implementation(libraries.compose.ui.tooling)
    implementation(libraries.compose.ui.tooling.preview)
    implementation(libraries.compose.runtime.livedata)
    implementation(libraries.compose.foundation)
    implementation(libraries.compose.material)
    implementation(libraries.compose.activity)

    implementation(libraries.koin.core)
    implementation(libraries.koin.android)

    implementation(libraries.kotlinx.datetime)

    implementation(libraries.lifecycle.viewmodel)
    implementation(libraries.lifecycle.viewmodel.compose)
    implementation(libraries.lifecycle.viewmodel.ktx)
    implementation(libraries.lifecycle.livedata.ktx)

    implementation(libraries.napier)

    implementation(libraries.coil.compose)
    implementation(libraries.coil.gif)

    implementation(libraries.exoplayer.core)
    implementation(libraries.exoplayer.common)
    implementation(libraries.exoplayer.ui)

    implementation(libraries.profileinstaller)
}
