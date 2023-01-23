plugins {
    id(Plugins.gradleAndroidApplication)
    kotlin(Plugins.Kotlin.android)
    id(Plugins.Kotlin.ksp) version Versions.ksp
    id(Plugins.jetBrainsCompose)
}

compose {
    kotlinCompilerPlugin.set(Deps.Compose.compiler)
}

android {
    namespace = Configs.Android.namespace
    compileSdk = Configs.Android.compileSdk
    defaultConfig {
        applicationId = "com.playtogether.kmp.android"
        minSdk = Configs.Android.minSdk
        targetSdk = Configs.Android.targetSdk
        versionCode = Configs.Android.versionCode
        versionName = Configs.Android.versionName
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(Deps.Projects.shared))
    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.material)
    implementation(compose.preview)

    implementation(Deps.Kotlin.coroutinesAndroid)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.androidCompose)

    // Compose Destinations
    implementation(Deps.composeDestinations)
    ksp(Deps.KSP.composeDestinations)
}
