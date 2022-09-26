package com.kapas.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    override val tableName: String = "user"

    val uid = varchar("uid", 128)
    val cardNumber = varchar("card_number", 64).nullable()
    val name = varchar("name", 64).nullable()
    val address = varchar("address", 512).nullable()
    val birthPlace = varchar("birth_place", 128).nullable()
    val birthDate = varchar("birth_date", 128).nullable()
    val email = varchar("email", 128)
    val phone = varchar("phone", 64).nullable()
    val avatarUrl = varchar("avatar_url",512).nullable()
    val gender = varchar("gender",64).nullable()
    val balance = double("balance")
    val income = double("income")
    val outcome = double("outcome")
    val point = integer("point")
    val score = integer("score")
    val rank = integer("rank")

    override val primaryKey: PrimaryKey = PrimaryKey(uid)
}