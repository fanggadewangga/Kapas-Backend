package com.kapas

import com.kapas.di.databaseModule
import com.kapas.di.repositoryModule
import com.kapas.di.routeModule
import com.kapas.plugins.configureRouting
import com.kapas.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

@KtorExperimentalLocationsAPI
fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        install(Koin) {
            slf4jLogger(Level.ERROR)
            modules(listOf(databaseModule, repositoryModule, routeModule))
        }
        install(Locations)
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
