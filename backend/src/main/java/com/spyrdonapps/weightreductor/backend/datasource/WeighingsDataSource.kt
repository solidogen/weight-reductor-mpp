package com.spyrdonapps.weightreductor.backend.datasource

import com.spyrdonapps.common.model.*
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.weightreductor.backend.database.tables.WeighingsTable
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface WeighingsDataSource {
    suspend fun getAllWeighings(): List<Weighing>
    suspend fun upsert(weighing: Weighing)
    suspend fun getByDate(date: Instant): Weighing
    suspend fun getById(id: Long): Weighing
    suspend fun deleteByDate(date: Instant)
}

class ExposedWeighingsDataSource() : WeighingsDataSource {

    override suspend fun getAllWeighings(): List<Weighing> =
        newSuspendedTransaction {
            WeighingsTable.selectAll().map {
                Weighing(
                    weight = it[WeighingsTable.weight],
                    date = it[WeighingsTable.date].toKotlinInstant()
                )
            }
        }

    override suspend fun upsert(weighing: Weighing) {
        newSuspendedTransaction {
            val existingWeighingWithDate =
                WeighingsTable.select { WeighingsTable.date eq weighing.date.toJavaInstant() }
                    .firstOrNull()
            if (existingWeighingWithDate != null) {
                WeighingsTable.update(where = { WeighingsTable.date eq weighing.date.toJavaInstant() }) {
                    it[weight] = weighing.weight
                }
            } else {
                WeighingsTable.insert {
                    it[date] = weighing.date.toJavaInstant()
                    it[weight] = weighing.weight
                }
            }
        }
    }

    override suspend fun getById(id: Long): Weighing =
        newSuspendedTransaction {
            // there is no ids in table, just trying if query params work
            return@newSuspendedTransaction WeighingsTable.selectAll().limit(1).map {
                Weighing(
                    weight = it[WeighingsTable.weight],
                    date = it[WeighingsTable.date].toKotlinInstant()
                )
            }.first()
        }

    override suspend fun getByDate(date: Instant): Weighing =
        newSuspendedTransaction {
            WeighingsTable.select { WeighingsTable.date eq date.toJavaInstant() }.first().let {
                Weighing(
                    date = it[WeighingsTable.date].toKotlinInstant(),
                    weight = it[WeighingsTable.weight]
                )
            }
        }

    override suspend fun deleteByDate(date: Instant) {
        newSuspendedTransaction {
            val deleteCount =
                WeighingsTable.deleteWhere { WeighingsTable.date eq date.toJavaInstant() }
            if (deleteCount == 0) error("Could not delete weighing for date $date")
        }
    }
}