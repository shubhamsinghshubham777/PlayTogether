import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin(Plugins.Kotlin.jvm)
    id(Plugins.jetBrainsCompose)
}

group = Configs.Desktop.group
version = Configs.Desktop.version

dependencies {
    with(compose) {
        implementation(desktop.currentOs)
    }
    implementation(project(Deps.Projects.shared))
}

compose {
    compose.kotlinCompilerPlugin
}

compose.desktop {
    application {
        mainClass = "com/playtogether/kmp/desktop/MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = Configs.Desktop.packageName
            packageVersion = Configs.Desktop.version
        }
    }
}