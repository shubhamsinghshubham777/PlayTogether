plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlin-parcelize")
    id("dev.icerock.moko.kswift") version "0.6.1"
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.2"
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature) {
        filter = includeFilter("ClassContext/PlayTogetherKMP:shared/com/playtogether/kmp/data/models/UIState")
    }
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js { browser() }
    jvm()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            linkerOpts("-ObjC")
        }
        pod(name = "KMPNativeCoroutinesRxSwift", version = "0.13.2")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                api("io.insert-koin:koin-core:3.3.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        // Mobile dependencies are only meant for Android and iOS modules i.e. we can use KMM libs here
        val mobileMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("dev.icerock.moko:kswift-runtime:0.6.1")
            }
        }
        val androidMain by getting {
            dependsOn(mobileMain)
            dependencies {
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(mobileMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
        val jsMain by getting
        val jsTest by getting
    }
}

android {
    namespace = "com.playtogether.kmp"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}
