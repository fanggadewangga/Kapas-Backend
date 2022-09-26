package com.kapas.route.user

import com.kapas.data.IKapasRepository
import com.kapas.model.history.HistoryBody
import com.kapas.model.user.EditVerificationBody
import com.kapas.model.user.UserBody
import com.kapas.route.RouteResponseHelper.generalException
import com.kapas.route.RouteResponseHelper.generalListSuccess
import com.kapas.route.RouteResponseHelper.generalSuccess
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.locations.put
import io.ktor.locations.post
import io.ktor.request.*
import java.lang.Exception

@KtorExperimentalLocationsAPI
class UserRoute(
    private val repository: IKapasRepository
) {

    private fun Route.addUser(){
        post<UserRouteLocation.UserAddRoute> {
            val body = try {
                call.receive<UserBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }
            call.generalSuccess { repository.addUser(body) }
        }
    }

    private fun Route.getUserDetail() {
        get<UserRouteLocation.UserGetDetailRoute> {
            val uid = try {
                call.parameters["uid"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            }
            call.generalSuccess { repository.getUserDetail(uid!!) }
        }
    }

    private fun Route.updateUserVerification() {
        put<UserRouteLocation.UserUpdateRoute> {
            val uid = try {
                call.parameters["uid"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }

            val body = try {
                call.receive<EditVerificationBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }

            call.generalSuccess { repository.updateUserVerification(uid!!, body) }
        }
    }

    private fun Route.updateUser() {
        put<UserRouteLocation.UserUpdateRoute> {
            val uid = try {
                call.parameters["uid"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }

            val body = try {
                call.receive<UserBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }

            call.generalSuccess { repository.updateUser(uid!!, body) }
        }
    }


    private fun Route.getLeaderboard() {
        get<UserRouteLocation.LeaderboardGetListRoute> {
            call.generalListSuccess { repository.getLeaderboard() }
        }
    }

    private fun Route.postHistory() {
        post<UserRouteLocation.HistoryPostRoute> {
            try {
                call.parameters["uid"]
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }

            val body = try {
                call.receive<HistoryBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }

            call.generalSuccess { repository.addHistory(body) }
        }
    }

    private fun Route.getHistoriesByUser() {
        get<UserRouteLocation.HistoryGetListRoute> {
            val uid = try {
                call.parameters["uid"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            }

            call.generalListSuccess { repository.getHistoriesByUser(uid!!) }
        }
    }

    fun initUserRoute(route: Route) {
        route.apply {
            addUser()
            getUserDetail()
            updateUser()
            updateUserVerification()
            getLeaderboard()
            postHistory()
            getHistoriesByUser()
        }
    }

}