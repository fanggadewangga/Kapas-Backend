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

    /*
    * Todo : DELETE JOB
    * */

    suspend fun addUser(body: UserBody) // not clear
    suspend fun getUserDetail(uid: String): UserResponse // not clear
    suspend fun updateUser(uid: String, body: UserBody) // not clear
    suspend fun getLeaderboard(): List<LeaderboardResponse> // half clear
    suspend fun addJob(body: JobBody) // not clear
    suspend fun getJobDetail(jobId: String): JobResponse // half clear
    suspend fun getAllJobs(): List<JobListResponse> // half clear
    suspend fun searchJob(query: String): List<JobListResponse> // not clear
    suspend fun addHistory(uid:String, body: HistoryBody) // not clear

    suspend fun getHistoriesByUser(uid: String): List<HistoryResponse> // not clear
}