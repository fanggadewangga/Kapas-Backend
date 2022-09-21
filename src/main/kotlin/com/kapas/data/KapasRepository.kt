package com.kapas.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.kapas.data.database.DatabaseFactory
import com.kapas.data.table.JobTable
import com.kapas.data.table.UserTable
import com.kapas.model.job.JobBody
import com.kapas.model.job.JobListResponse
import com.kapas.model.job.JobResponse
import com.kapas.model.user.UserBody
import com.kapas.model.user.UserResponse
import com.kapas.util.Mapper
import org.jetbrains.exposed.sql.*
import java.util.*

class KapasRepository(
    private val dbFactory: DatabaseFactory
) : IKapasRepository {

    override suspend fun addUser(body: UserBody) {
        dbFactory.dbQuery {
            UserTable.insert { table ->
                table[uid] = body.uid
                table[cardNumber] = body.cardNumber
                table[name] = body.name
                table[address] = body.address
                table[birthPlace] = body.birthPlace
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

    override suspend fun getUserDetail(uid: String): UserResponse =
        dbFactory.dbQuery {
            UserTable.select {
                UserTable.uid.eq(uid)
            }.mapNotNull {
                Mapper.mapRowToUserResponse(it)
            }
        }.first()


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
            UserTable.selectAll()
                .orderBy(UserTable.score, SortOrder.DESC)
                .mapNotNull { Mapper.mapRowToLeaderboardResponse(it) }
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
                table[imageUrl] = body.imageUrl
                table[latitude] = body.latitude
                table[longitude] = body.longitude
            }
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
                .mapNotNull {
                    Mapper.mapRowToJobResponse(it)
                }
        }.first()

    override suspend fun getAllJobs(): List<JobListResponse> =
        dbFactory.dbQuery {
            JobTable.selectAll()
                .orderBy(JobTable.title, SortOrder.ASC)
                .mapNotNull { Mapper.mapRowToJobListResponse(it) }
        }

    override suspend fun searchJob(query: String): List<JobListResponse> =
        dbFactory.dbQuery {
            JobTable.select {
                LowerCase(JobTable.title).like("%query%".lowercase(Locale.getDefault()))
            }.groupBy(JobTable.jobId)
                .mapNotNull {
                    Mapper.mapRowToJobListResponse(it)
                }
        }

}