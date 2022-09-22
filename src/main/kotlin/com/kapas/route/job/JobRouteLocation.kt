package com.kapas.route.job

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
class JobRouteLocation {

    companion object {
        // GET (include query to search job)
        const val JOB = "/job"

        // POST
        const val POST_JOB = "/job"

        // GET
        const val DETAIL_JOB = "$JOB/{jobId}"

        // DELETE (not yet implemented)
        const val DELETE_JOB = "$JOB/{jobId}"
    }

    @Location(JOB)
    class JobGetListRoute

    @Location(POST_JOB)
    class JobPostRoute

    @Location(DETAIL_JOB)
    data class JobGetDetailRoute(val jobId: String)

    @Location(DELETE_JOB)
    data class JobDeleteRoute(val jobId: String)

}