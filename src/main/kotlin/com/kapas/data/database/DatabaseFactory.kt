package com.kapas.data.database

import com.kapas.data.table.HistoryTable
import com.kapas.data.table.JobTable
import com.kapas.data.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(
    private val dataSource: HikariDataSource
) {

    init {
        Database.connect(dataSource)
        transaction {
            val tables = listOf(
                UserTable, JobTable, HistoryTable
            )
            tables.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}