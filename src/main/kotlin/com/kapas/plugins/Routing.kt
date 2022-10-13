package com.kapas.plugins

import com.kapas.route.job.JobRoute
import com.kapas.route.user.UserRoute
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {
    val userRoute by inject<UserRoute>()
    val jobRoute by inject<JobRoute>()

    routing {
        userRoute.initUserRoute(this)
        jobRoute.initJobRoute(this)
    }
}
