plugins {
    kotlin(Plugins.Kotlin.jvm)
    id(Plugins.jetBrainsCompose)
}

group = Configs.Desktop.group
version = Configs.Desktop.version
repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(Deps.Projects.shared))
}

compose {
    kotlinCompilerPlugin.set(Deps.Compose.compiler)
}

task("wrapper") {}
task("prepareKotlinIdeaImport") {}
task("prepareKotlinBuildScriptModel") {}

compose.desktop {
    application {
        mainClass = "com/playtogether/kmp/desktop/MainKt"
    }
}
