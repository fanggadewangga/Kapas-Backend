package com.kapas.data.table

import org.jetbrains.exposed.sql.Table

object HistoryTable: Table() {

    override val tableName: String = "history"

    val uid = reference("uid", UserTable.uid)
    val jobId = reference("job_id", JobTable.jobId)
    val transactionId = varchar("transaction_id", 128)

    override val primaryKey: PrimaryKey = PrimaryKey(transactionId)
}