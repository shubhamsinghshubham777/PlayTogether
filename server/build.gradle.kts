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

dependencies {
    implementation(project(Deps.Projects.shared))
    with(Deps.KtorServer) {
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
    with(Deps.Koin) {
        implementation(ktor)
        implementation(slf4jLogger)
    }
    with(Deps.Exposed) {
        implementation(core)
        implementation(dao)
        implementation(jdbc)
    }
    implementation(Deps.apacheCommonsCodec)
    implementation(Deps.logback)
    implementation(Deps.postgresql)
    implementation(Deps.hikari)
    testImplementation(Deps.Kotlin.jUnit)
}
