plugins {
    id(Plugins.gradleAndroidLibrary) // Need to keep this before the parcelize plugin otherwise the build won't succeed
    with(Plugins.Kotlin) {
        kotlin(multiplatform)
        kotlin(cocoapods)
        id(parcelize)
        kotlin(serialization)
    }
    id(Plugins.mokoKSwift) version Versions.mokoKSwift
    id(Plugins.nativeCoroutines) version Versions.nativeCoroutines
    id(Plugins.sqlDelight)
}

repositories {
    google()
    mavenCentral()
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature) {
        filter = includeFilter("ClassContext/PlayTogetherKMP:shared/com/playtogether/kmp/data/util/UIState")
    }
}

sqldelight {
    database(Configs.SQLDelight.dbName) {
        packageName = Configs.SQLDelight.packageName
        schemaOutputDirectory = file(Configs.SQLDelight.schemaOutputDirectory)
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
        summary = Configs.Cocoapods.summary
        homepage = Configs.Cocoapods.homepage
        version = Configs.Cocoapods.version
        ios.deploymentTarget = Configs.Cocoapods.iosDeploymentTarget
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            linkerOpts("-ObjC")
        }
        pod(name = Deps.Pods.nativeCoroutines, version = Versions.nativeCoroutines)
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.coroutinesCore)
                implementation(Deps.Kotlin.serialization)
                api(Deps.Koin.core)
                implementation(Deps.KtorClient.core)
                with(Deps.SQLDelight) {
                    implementation(runtime)
                    implementation(coroutinesExtensions)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Deps.Kotlin.testCommon))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Deps.KtorClient.cioEngine)
                implementation(Deps.SQLDelight.jvm)
            }
        }
        val jvmTest by getting
        // Mobile dependencies are only meant for Android and iOS modules i.e. we can use KMM libs here
        val mobileMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(Deps.mokoKSwiftRuntime)
            }
        }
        val androidMain by getting {
            dependsOn(mobileMain)
            dependencies {
                implementation(Deps.viewModelKtx)
                implementation(Deps.KtorClient.androidEngine)
                implementation(Deps.SQLDelight.android)
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
            dependencies {
                implementation(Deps.KtorClient.iOSEngine)
                implementation(Deps.SQLDelight.native)
            }
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
        val jsMain by getting {
            dependencies {
                implementation(Deps.KtorClient.jsEngine)
                implementation(Deps.SQLDelight.js)
            }
        }
        val jsTest by getting
    }
}

android {
    namespace = Configs.Android.namespaceShared
    compileSdk = Configs.Android.compileSdk
    defaultConfig {
        minSdk = Configs.Android.minSdk
        targetSdk = Configs.Android.targetSdk
    }
}
