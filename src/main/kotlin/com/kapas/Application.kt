package com.kapas

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.kapas.plugins.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "localhost") {
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
