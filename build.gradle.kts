plugins {
    //trick: for the same plugin versions in all submodules
    id("com.android.application").version("7.3.0").apply(false)
    id("com.android.library").version("7.3.0").apply(false)
    kotlin("android").version("1.7.21").apply(false)
    kotlin("js").version("1.7.21").apply(false)
    kotlin("jvm").version("1.7.21").apply(false)
    kotlin("multiplatform").version("1.7.21").apply(false)
    kotlin("plugin.serialization").version("1.7.21").apply(false)
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
