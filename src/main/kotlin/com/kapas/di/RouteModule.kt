package com.kapas.di

import com.kapas.route.job.JobRoute
import com.kapas.route.user.UserRoute
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
val routeModule = module {
    factory { UserRoute(get()) }
    factory { JobRoute(get()) }
}