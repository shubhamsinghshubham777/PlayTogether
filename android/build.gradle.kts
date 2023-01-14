plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version("1.7.21-1.0.8")
}

android {
    namespace = "com.playtogether.kmp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.playtogether.kmp.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
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
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling:1.4.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-alpha02")
    implementation("androidx.compose.foundation:foundation:1.4.0-alpha02")
    implementation("androidx.compose.material:material:1.4.0-alpha02")
    implementation("androidx.compose.material:material-icons-extended:1.4.0-alpha02")

    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.insert-koin:koin-android:3.3.1")
    implementation("io.insert-koin:koin-androidx-compose:3.3.0")

    // Compose Destinations
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.7.30-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.7.30-beta")
}
