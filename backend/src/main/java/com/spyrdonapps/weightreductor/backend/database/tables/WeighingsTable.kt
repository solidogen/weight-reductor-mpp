package com.spyrdonapps.weightreductor.backend.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.timestamp

object WeighingsTable : IntIdTable() {
    val weight = float("weight")
    val date = timestamp("date")
}