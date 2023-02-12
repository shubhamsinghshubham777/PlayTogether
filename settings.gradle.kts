pluginManagement {
    repositories {
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "PlayTogetherKMP"
include(":android")
include(":desktop")
include(":shared")
include(":web")
include(":server")
