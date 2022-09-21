package com.kapas.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    override val tableName: String = "user"

    val uid = varchar("uid", 128)
    val cardNumber = varchar("card_number", 64)
    val name = varchar("name", 64)
    val address = varchar("address", 512)
    val birthPlace = varchar("birth_place", 128)
    val birthDate = varchar("birth_date", 128)
    val email = varchar("email", 128)
    val phone = varchar("phone", 64)
    val avatarUrl = varchar("avatar_url",512)
    val gender = varchar("gender",64)
    val balance = double("balance")
    val income = double("income")
    val outcome = double("outcome")
    val point = integer("point")
    val score = integer("score")
    val rank = integer("rank")

    override val primaryKey: PrimaryKey? = PrimaryKey(uid)
}