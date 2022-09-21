package com.kapas.data.table

import org.jetbrains.exposed.sql.Table

object JobTable: Table() {

    override val tableName: String = "job"

    val jobId = varchar("job_id", 128)
    val title = varchar("title", 128)
    val posterId = reference("uid", UserTable.uid)
    val location = varchar("location", 128)
    val wage = double("wage")
    val address = varchar("address", 512)
    val description = varchar("description", 1024)
    val imageUrl = varchar("image_url",1024)
    val latitude = double("latitude")
    val longitude = double("longitude")

    override val primaryKey: PrimaryKey? = PrimaryKey(jobId)
}