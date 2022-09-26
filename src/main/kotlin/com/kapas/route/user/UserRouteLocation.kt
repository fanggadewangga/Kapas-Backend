package com.kapas.route.user

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
class UserRouteLocation {

    companion object {
        private const val USER = "/user"

        // POST
        const val POST_USER = USER
        private const val SELECTED_USER = "$USER/{uid}"

        // GET
        const val DETAIL_USER = SELECTED_USER

        // UPDATE
        const val UPDATE_USER = SELECTED_USER

        // GET
        const val LEADERBOARD = "/leaderboard"

        // POST
        const val POST_HISTORY = "$SELECTED_USER/history"

        // GET
        const val GET_HISTORY = "$SELECTED_USER/history"
    }

    @Location(POST_USER)
    class UserAddRoute

    @Location(DETAIL_USER)
    data class UserGetDetailRoute(val uid: String)

    @Location(UPDATE_USER)
    data class UserUpdateRoute(val uid: String)

    @Location(LEADERBOARD)
    class LeaderboardGetListRoute

    @Location(POST_HISTORY)
    data class HistoryPostRoute(val uid: String)

    @Location(GET_HISTORY)
    data class HistoryGetListRoute(val uid: String)
}