package com.spyrdonapps.weightreductor.backend.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.time.Instant

object WeighingsTable : LongIdTable(name = "weighings") {
    val weight: Column<Float> = float("weight")
    val date: Column<Instant> = timestamp("date")
}

object UsersTable : LongIdTable(name = "users") {
    val username: Column<String> = varchar("username", 30).uniqueIndex()
    val passwordHash: Column<String> = varchar("password_hash", 100)
}

object RefreshTokens : Table(name = "refresh_tokens") {
    val refreshToken: Column<String> = varchar("refresh_token", 500)
    val userId: Column<EntityID<Long>> = reference("user_id", UsersTable)
}