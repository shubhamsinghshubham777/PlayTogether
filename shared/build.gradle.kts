@file:Suppress("UNUSED_VARIABLE", "UnstableApiUsage")

import co.touchlab.kermit.gradle.StripSeverity
import org.jetbrains.compose.ExperimentalComposeLibrary

enum class BuildType(val value: String) {
    DEBUG("debug"),
    RELEASE("release")
}

object EnvironmentVariables {
    const val BuildType = "BUILD_TYPE"
}

fun systemEnv(name: String): String? {
    return try {
        System.getenv(name)
    } catch (e: Exception) {
        null
    }
}

plugins {
    id(Plugins.gradleAndroidLibrary) // Need to keep this before the parcelize plugin otherwise the build won't succeed
    with(Plugins.Kotlin) {
        kotlin(multiplatform)
        id(parcelize)
        kotlin(serialization)
    }
    id(Plugins.mokoKSwift) version Versions.mokoKSwift
    id(Plugins.nativeCoroutines) version Versions.nativeCoroutines
    id(Plugins.kermit) version Versions.kermit
    id(Plugins.jetBrainsCompose)
}

kswift {
    iosDeploymentTarget.set("11.0")
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature) {
        filter = includeFilter("ClassContext/PlayTogetherKMP:shared/com/playtogether/kmp/presentation/util/UIState")
    }
}

kermit {
    stripBelow = systemEnv(EnvironmentVariables.BuildType)?.let { buildType ->
        when(BuildType.valueOf(buildType)) {
            BuildType.DEBUG -> StripSeverity.None
            BuildType.RELEASE -> StripSeverity.All
        }
    } ?: StripSeverity.None
}

kotlin {
    android()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = Configs.iOS.frameworkBaseName
        }
    }
    js(IR) { browser() }
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Deps.Kotlin) {
                    implementation(coroutinesCore)
                    implementation(serialization)
                    api(dateTime)
                }
                api(Deps.Koin.core)
                with(Deps.KtorClient) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(kotlinxJsonSerialization)
                    implementation(Deps.logback)
                    implementation(logging)
                    implementation(auth)
                }
                implementation(Deps.kermit)
                implementation(Deps.multiplatformSettingsNoArg)
                implementation(compose.runtime)
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
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(mobileMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(compose.runtime)
                implementation(Deps.KtorClient.iOSEngine)
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
            }
        }
        val jsTest by getting

        // For Android and Desktop
        val composeMain by creating {
            dependsOn(commonMain)
            androidMain.dependsOn(this)
            jvmMain.dependsOn(this)
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.ui)
                @OptIn(ExperimentalComposeLibrary::class) api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.animation)
                api(compose.animationGraphics)
                api(compose.preview)
                api(Deps.precompose)
            }
        }
        val composeTest by creating {
            dependsOn(commonTest)
            androidUnitTest.dependsOn(this)
            jvmTest.dependsOn(this)
        }
    }
}

android {
    namespace = Configs.Android.namespaceShared
    compileSdk = Configs.Android.compileSdk
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    defaultConfig {
        minSdk = Configs.Android.minSdk
    }
}
