package com.kapas.data

import com.kapas.model.history.HistoryBody
import com.kapas.model.history.HistoryResponse
import com.kapas.model.job.JobBody
import com.kapas.model.job.JobListResponse
import com.kapas.model.job.JobResponse
import com.kapas.model.leaderboard.LeaderboardResponse
import com.kapas.model.user.UserBody
import com.kapas.model.user.UserResponse

interface IKapasRepository {

    suspend fun addUser(body: UserBody) // clear (fix: nullable attr)
    suspend fun getUserDetail(uid: String): UserResponse // clear
    suspend fun updateUser(uid: String, body: UserBody) // not clear (not knowing to handle different bodies)
    suspend fun getLeaderboard(): List<LeaderboardResponse> // clear
    suspend fun addJob(body: JobBody) // clear
    suspend fun deleteJob(jobId: String) // clear
    suspend fun getJobDetail(jobId: String): JobResponse // not clear
    suspend fun getAllJobs(): List<JobListResponse> // clear
    suspend fun searchJob(query: String): List<JobListResponse> // not clear
    suspend fun addHistory(uid:String, body: HistoryBody) // clear
    suspend fun getHistoriesByUser(uid: String): List<HistoryResponse> // not clear
}