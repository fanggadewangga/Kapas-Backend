package com.kapas.util

import com.kapas.data.table.JobTable
import com.kapas.data.table.UserTable
import com.kapas.model.job.JobListResponse
import com.kapas.model.job.JobResponse
import com.kapas.model.leaderboard.LeaderboardResponse
import com.kapas.model.user.UserResponse
import org.jetbrains.exposed.sql.ResultRow

object Mapper {

    fun mapRowToJobResponse(row: ResultRow?): JobResponse? {
        if (row == null)
            return null

        return JobResponse(
            jobId = row[JobTable.jobId],
            title = row[JobTable.title],
            posterId = row[UserTable.uid],
            posterName = row[UserTable.name],
            posterAvatarUrl = row[UserTable.avatarUrl],
            location = row[JobTable.location],
            wage = row[JobTable.wage],
            address = row[JobTable.address],
            description = row[JobTable.description],
            imageUrl = row[JobTable.imageUrl],
            latitude = row[JobTable.latitude],
            longitude = row[JobTable.longitude]
        )
    }


    fun mapRowToJobListResponse(row: ResultRow?): JobListResponse? {
        if (row == null)
            return null

        return JobListResponse(
            jobId = row[JobTable.jobId],
            title = row[JobTable.title],
            description = row[JobTable.description]
        )
    }


    fun mapRowToUserResponse(row: ResultRow?): UserResponse? {
        if (row == null)
            return null

        return UserResponse(
            uid = row[UserTable.uid],
            cardNumber = row[UserTable.cardNumber],
            name = row[UserTable.name],
            address = row[UserTable.address],
            birthPlace = row[UserTable.birthPlace],
            birthDate = row[UserTable.birthDate],
            email = row[UserTable.email],
            phone = row[UserTable.phone],
            avatarUrl = row[UserTable.avatarUrl],
            gender = row[UserTable.gender],
            balance = row[UserTable.balance],
            income = row[UserTable.income],
            outcome = row[UserTable.outcome],
            point = row[UserTable.point],
            score = row[UserTable.score],
            rank = row[UserTable.rank]
        )
    }

    fun mapRowToLeaderboardResponse(row: ResultRow?): LeaderboardResponse? {
        if (row == null)
            return null

        return LeaderboardResponse(
            uid = row[UserTable.uid],
            name = row[UserTable.name],
            score = row[UserTable.score],
            avatarUrl = row[UserTable.avatarUrl]
        )
    }

}