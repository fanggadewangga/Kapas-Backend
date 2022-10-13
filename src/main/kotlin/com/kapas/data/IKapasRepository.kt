package com.kapas.data

import com.kapas.model.history.HistoryBody
import com.kapas.model.history.HistoryResponse
import com.kapas.model.job.EditJobImageBody
import com.kapas.model.job.JobBody
import com.kapas.model.job.JobListResponse
import com.kapas.model.job.JobResponse
import com.kapas.model.leaderboard.LeaderboardResponse
import com.kapas.model.user.*

interface IKapasRepository {

    suspend fun addUser(body: UserBody) // clear
    suspend fun getUserDetail(uid: String): UserResponse // clear
    suspend fun updateUserVerification(uid: String, body: EditVerificationBody) // clear
    suspend fun updateUser(uid: String, body: UserBody) // clear
    suspend fun getLeaderboard(): List<LeaderboardResponse> // clear
    suspend fun addJob(body: JobBody) // clear
    suspend fun deleteJob(jobId: String) // clear
    suspend fun getJobDetail(jobId: String): JobResponse // clear
    suspend fun updateJobImage(jobId: String, body: EditJobImageBody) // clear
    suspend fun getAllJobs(): List<JobListResponse> // clear
    suspend fun searchJob(query: String): List<JobListResponse> // clear
    suspend fun addHistory(body: HistoryBody) // clear
    suspend fun getHistoriesByUser(uid: String): List<HistoryResponse> // not clear
}