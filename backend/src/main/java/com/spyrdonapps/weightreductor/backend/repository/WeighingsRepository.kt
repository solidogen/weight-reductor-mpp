package com.spyrdonapps.weightreductor.backend.repository

import com.spyrdonapps.common.model.*
import com.spyrdonapps.common.devonly.*
import com.spyrdonapps.weightreductor.backend.datasource.WeighingsDataSource
import kotlinx.datetime.Instant

// todo - I can get rid of this class and operate on the datasource itself
//  also client Repository has almost the same name
class WeighingsRepository(private val weighingsDataSource: WeighingsDataSource) {

    suspend fun getAllWeighings(): List<Weighing> = weighingsDataSource.getAllWeighings()
    suspend fun upsert(weighing: Weighing) = weighingsDataSource.upsert(weighing)
    suspend fun getByDate(date: Instant): Weighing = weighingsDataSource.getByDate(date)
    suspend fun getById(id: Long): Weighing = weighingsDataSource.getById(id)
    suspend fun deleteByDate(date: Instant) = weighingsDataSource.deleteByDate(date)
}