plugins {
    id(Plugins.gradleAndroidApplication) version(Versions.gradleAndroid) apply(false)
    id(Plugins.gradleAndroidLibrary) version(Versions.gradleAndroid) apply(false)
    with(Plugins.Kotlin) {
        kotlin(android) version(Versions.kotlin) apply(false)
        kotlin(js) version(Versions.kotlin) apply(false)
        kotlin(jvm) version(Versions.kotlin) apply(false)
        kotlin(multiplatform) version(Versions.kotlin) apply(false)
        kotlin(serialization) version(Versions.kotlin) apply(false)
    }
    id(Plugins.jetBrainsCompose) version(Versions.jetbrainsCompose) apply(false)
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
