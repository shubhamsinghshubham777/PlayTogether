@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.gradleAndroidApplication)
    kotlin(Plugins.Kotlin.android)
    id(Plugins.jetBrainsCompose)
}

compose {
    compose.kotlinCompilerPlugin
}

android {
    namespace = Configs.Android.namespace
    compileSdk = Configs.Android.compileSdk
    defaultConfig {
        applicationId = Configs.Android.applicationId
        minSdk = Configs.Android.minSdk
        targetSdk = Configs.Android.targetSdk
        versionCode = Configs.Android.versionCode
        versionName = Configs.Android.versionName
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(Deps.Projects.shared))
    implementation(compose.uiTooling)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.compose)
    implementation(Deps.Accompanist.systemUiController)
}
