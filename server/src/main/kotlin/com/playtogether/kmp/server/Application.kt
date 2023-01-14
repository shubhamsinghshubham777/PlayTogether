package com.playtogether.kmp.server

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing


fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
        watchPaths = listOf(
            "PlayTogetherKMP/server/build/classes/kotlin/main/com/playtogether/kmp/server"
        )
    ).start(wait = true)
}

fun Application.module() {
    routing {
        get("/") { call.respondText("Welcome to PlayTogether!") }
    }
}
