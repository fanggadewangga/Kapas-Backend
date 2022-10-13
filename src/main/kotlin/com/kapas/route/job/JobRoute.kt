package com.kapas.route.job

import com.kapas.data.IKapasRepository
import com.kapas.model.job.EditJobImageBody
import com.kapas.model.job.JobBody
import com.kapas.model.user.EditVerificationBody
import com.kapas.route.RouteResponseHelper.generalException
import com.kapas.route.RouteResponseHelper.generalListSuccess
import com.kapas.route.RouteResponseHelper.generalSuccess
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.request.*
import java.lang.Exception

@KtorExperimentalLocationsAPI
class JobRoute(
    private val repository: IKapasRepository,
) {

    private fun Route.getAllJobs() {
        get<JobRouteLocation.JobGetListRoute> {
            val query = try {
                call.request.queryParameters["q"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            }

            if (query != null)
                call.generalListSuccess { repository.searchJob(query) }
            else
                call.generalSuccess { repository.getAllJobs() }
        }
    }

    private fun Route.postJob() {
        post<JobRouteLocation.JobPostRoute> {
            val body = try {
                call.receive<JobBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }
            call.generalSuccess { repository.addJob(body) }
        }
    }

    private fun Route.getJobDetail() {
        get<JobRouteLocation.JobGetDetailRoute> {
            val jobId = try {
                call.parameters["jobId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            }
            call.generalSuccess { repository.getJobDetail(jobId!!) }
        }
    }

    private fun Route.updateJobImage() {
        put<JobRouteLocation.JobEditImageRoute> {
            val jobId = try {
                call.parameters["jobId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }

            val body = try {
                call.receive<EditJobImageBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }

            call.generalSuccess { jobId?.let { it1 -> repository.updateJobImage(it1, body) } }
        }
    }

    private fun Route.deleteJob() {
        delete<JobRouteLocation.JobDeleteRoute> {
            val jobId = try {
                call.parameters["jobId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@delete
            }
            call.generalSuccess { repository.deleteJob(jobId!!) }
        }
    }

    fun initJobRoute(route: Route) {
        route.apply {
            getAllJobs()
            postJob()
            getJobDetail()
            updateJobImage()
            deleteJob()
        }
    }
}