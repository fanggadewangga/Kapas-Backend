package com.kapas.plugins

import com.kapas.route.job.JobRoute
import com.kapas.route.user.UserRoute
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {
    val userRoute by inject<UserRoute>()
    val jobRoute by inject<JobRoute>()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/leaderboard"){
            call.respondText("Hello from leaderboard")
        }

        userRoute.initUserRoute(this)
        jobRoute.initJobRoute(this)
    }
}
