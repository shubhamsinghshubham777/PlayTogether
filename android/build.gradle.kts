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
    packaging {
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
    implementation(compose.uiTooling)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.compose)
    implementation(Deps.Accompanist.systemUiController)
}
