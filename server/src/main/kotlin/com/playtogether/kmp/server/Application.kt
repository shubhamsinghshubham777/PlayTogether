package com.playtogether.kmp.server

import com.playtogether.kmp.server.plugins.Database
import com.playtogether.kmp.server.plugins.setupCors
import com.playtogether.kmp.server.plugins.setupDependencyInjection
import com.playtogether.kmp.server.plugins.setupRouting
import com.playtogether.kmp.server.plugins.setupSecurity
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    setupCors()
    setupDependencyInjection()
    Database.initDb()
    setupSecurity()
    setupRouting()
}
