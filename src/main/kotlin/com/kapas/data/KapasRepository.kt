package com.kapas.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.kapas.data.database.DatabaseFactory
import com.kapas.data.table.HistoryTable
import com.kapas.data.table.JobTable
import com.kapas.data.table.UserTable
import com.kapas.model.history.HistoryBody
import com.kapas.model.job.EditJobImageBody
import com.kapas.model.job.JobBody
import com.kapas.model.job.JobListResponse
import com.kapas.model.job.JobResponse
import com.kapas.model.leaderboard.LeaderboardResponse
import com.kapas.model.user.EditVerificationBody
import com.kapas.model.user.UserBody
import com.kapas.model.user.UserResponse
import com.kapas.util.Mapper
import org.jetbrains.exposed.sql.*
import java.util.*

class KapasRepository(
    private val dbFactory: DatabaseFactory,
) : IKapasRepository {

    override suspend fun addUser(body: UserBody) {
        dbFactory.dbQuery {
            UserTable.insert { table ->
                table[uid] = body.uid
                table[cardNumber] = body.cardNumber
                table[name] = body.name
                table[address] = body.address
                table[birthPlace] = body.birthPlace
                table[birthDate] = body.birthDate
                table[email] = body.email
                table[phone] = body.phone
                table[avatarUrl] = body.avatarUrl
                table[gender] = body.gender
                table[balance] = 0.0
                table[income] = 0.0
                table[outcome] = 0.0
                table[point] = 0
                table[score] = 0
                table[rank] = 0
            }
        }
    }

    override suspend fun getUserDetail(uid: String): UserResponse {
        val rank = getUserRank(uid)

        val selectedUser = dbFactory.dbQuery {
            UserTable.select {
                UserTable.uid.eq(uid)
            }.mapNotNull {
                Mapper.mapRowToUserResponse(it)
            }
        }.first()

        return UserResponse(
            uid = selectedUser.uid,
            cardNumber = selectedUser.cardNumber,
            name = selectedUser.name,
            address = selectedUser.address,
            birthPlace = selectedUser.birthPlace,
            birthDate = selectedUser.birthDate,
            email = selectedUser.email,
            phone = selectedUser.phone,
            avatarUrl = selectedUser.avatarUrl,
            gender = selectedUser.gender,
            balance = selectedUser.balance,
            income = selectedUser.income,
            outcome = selectedUser.outcome,
            point = selectedUser.point,
            score = selectedUser.score,
            rank = rank
        )
    }


    override suspend fun updateUserVerification(uid: String, body: EditVerificationBody) {
        val user = getUserDetail(uid)

        dbFactory.dbQuery {
            UserTable.update(
                where = { UserTable.uid.eq(uid) }
            ) { table ->
                table[cardNumber] = body.cardNumber
                table[name] = body.name
                table[address] = user.address
                table[birthPlace] = body.birthPlace
                table[email] = user.email
                table[phone] = user.phone
                table[avatarUrl] = user.avatarUrl
                table[gender] = body.gender
                table[balance] = user.balance
                table[income] = user.income
                table[outcome] = user.outcome
                table[point] = user.point
                table[score] = user.score
                table[rank] = user.rank
            }
        }
    }

    override suspend fun updateUser(uid: String, body: UserBody) {
        dbFactory.dbQuery {
            UserTable.update(
                where = { UserTable.uid.eq(uid) }
            ) { table ->
                table[cardNumber] = body.cardNumber
                table[name] = body.name
                table[address] = body.address
                table[birthPlace] = body.birthPlace
                table[email] = body.email
                table[phone] = body.phone
                table[avatarUrl] = body.avatarUrl
                table[gender] = body.gender
                table[balance] = body.balance
                table[income] = body.income
                table[outcome] = body.outcome
                table[point] = body.point
                table[score] = body.score
                table[rank] = body.rank
            }
        }
    }

    override suspend fun getLeaderboard() =
        dbFactory.dbQuery {
            val leaderboard = UserTable.selectAll()
                .orderBy(UserTable.score, SortOrder.DESC)
                .limit(50)
                .mapNotNull { Mapper.mapRowToLeaderboardResponse(it) }

            val leaderboardSize = leaderboard.size

            val list = mutableListOf<LeaderboardResponse>()

            for (rank in 0 until leaderboardSize) {
                val user = leaderboard[rank]
                list.add(
                    LeaderboardResponse(
                        uid = user.uid,
                        name = user.name,
                        score = user.score,
                        avatarUrl = user.avatarUrl,
                        rank = rank + 1
                    )
                )
            }
            return@dbQuery list
        }


    override suspend fun getUserRank(uid: String): Int {
        val leaderboard = dbFactory.dbQuery {
            UserTable.selectAll()
                .orderBy(UserTable.score, SortOrder.DESC)
                .mapNotNull { Mapper.mapRowToLeaderboardResponse(it) }
        }

        val selectedUser = dbFactory.dbQuery {
            UserTable.select { UserTable.uid.eq(uid) }.firstNotNullOf { Mapper.mapRowToLeaderboardResponse(it) }
        }

        return leaderboard.indexOf(selectedUser) + 1
    }

    override suspend fun addJob(body: JobBody) {
        dbFactory.dbQuery {
            JobTable.insert { table ->
                table[jobId] = "JOB${NanoIdUtils.randomNanoId()}"
                table[title] = body.title
                table[posterId] = body.posterId
                table[location] = body.location
                table[wage] = body.wage
                table[address] = body.address
                table[description] = body.description
                table[imageUrl] = null
                table[latitude] = body.latitude
                table[longitude] = body.longitude
            }
        }
    }

    override suspend fun deleteJob(jobId: String) {
        dbFactory.dbQuery {
            JobTable.deleteWhere { JobTable.jobId.eq(jobId) }
        }
    }

    override suspend fun getJobDetail(jobId: String): JobResponse =
        dbFactory.dbQuery {
            JobTable.join(UserTable, JoinType.LEFT) {
                JobTable.posterId.eq(UserTable.uid)
            }.slice(
                JobTable.jobId,
                JobTable.title,
                UserTable.uid,
                UserTable.name,
                UserTable.avatarUrl,
                JobTable.location,
                JobTable.wage,
                JobTable.address,
                JobTable.description,
                JobTable.imageUrl,
                JobTable.latitude,
                JobTable.longitude
            )
                .select {
                    JobTable.jobId.eq(jobId)
                }
                .groupBy(JobTable.jobId)
                .groupBy(UserTable.uid)
                .mapNotNull {
                    Mapper.mapRowToJobResponse(it)
                }
        }.first()

    override suspend fun updateJobImage(jobId: String, body: EditJobImageBody) {
        dbFactory.dbQuery {
            JobTable.update(
                where = { JobTable.jobId.eq(jobId) }
            ) { table ->
                table[imageUrl] = body.imageUrl
            }
        }
    }

    override suspend fun getAllJobs(): List<JobListResponse> =
        dbFactory.dbQuery {
            JobTable.selectAll()
                .orderBy(JobTable.title, SortOrder.ASC)
                .mapNotNull { Mapper.mapRowToJobListResponse(it) }
        }

    override suspend fun searchJob(query: String): List<JobListResponse> =
        dbFactory.dbQuery {
            JobTable.select {
                LowerCase(JobTable.title).like("%$query%".lowercase(Locale.getDefault()))
            }.groupBy(JobTable.jobId)
                .mapNotNull {
                    Mapper.mapRowToJobListResponse(it)
                }
        }

    override suspend fun addHistory(body: HistoryBody) {
        dbFactory.dbQuery {
            HistoryTable.insert { table ->
                table[uid] = body.uid
                table[jobId] = body.jobId
                table[transactionId] = "TRANSACTION${NanoIdUtils.randomNanoId()}"
            }
        }
    }

    override suspend fun getHistoriesByUser(uid: String) =
        dbFactory.dbQuery {
            HistoryTable.join(JobTable, JoinType.INNER) {
                HistoryTable.jobId.eq(JobTable.jobId)
            }.join(UserTable, JoinType.INNER) {
                HistoryTable.uid.eq(UserTable.uid)
            }.slice(
                HistoryTable.transactionId,
                JobTable.jobId,
                JobTable.title,
                JobTable.wage
            ).select {
                HistoryTable.uid.eq(uid)
            }
                .mapNotNull {
                    Mapper.mapRowToHistoryResponse(it)
                }
        }

}