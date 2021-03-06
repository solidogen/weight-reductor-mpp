package com.spyrdonapps.weightreductor.backend.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.time.Instant

object WeighingsTable : IntIdTable() {
    val weight: Column<Float> = float("weight")
    val date: Column<Instant> = timestamp("date")
}