plugins {
    with(Plugins.Kotlin) {
        kotlin(jvm)
        kotlin(serialization)
    }
    id(Plugins.ktor) version Versions.ktor
}

group = Configs.Server.group
version = Configs.Server.version
application {
    mainClass.set("com.playtogether.kmp.server.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Deps.Projects.shared))
    with(Deps.Ktor) {
        implementation(core)
        implementation(auth)
        implementation(authJwt)
        implementation(sessions)
        implementation(resources)
        implementation(hostCommon)
        implementation(statusPages)
        implementation(cors)
        implementation(compression)
        implementation(defaultHeaders)
        implementation(partialContent)
        implementation(callLogging)
        implementation(contentNegotiation)
        implementation(kotlinxSerialization)
        implementation(websockets)
        implementation(netty)
        testImplementation(tests)
    }
    implementation(Deps.logback)
    testImplementation(Deps.Kotlin.jUnit)
}
