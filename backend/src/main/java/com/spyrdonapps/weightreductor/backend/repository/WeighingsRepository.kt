package com.spyrdonapps.weightreductor.backend.repository

import com.spyrdonapps.weightreductor.backend.datasource.WeighingsDataSource
import com.spyrdonapps.weightreductor.backend.deletelater.Weighing
import kotlinx.datetime.Instant

class WeighingsRepository(private val weighingsDataSource: WeighingsDataSource) {

    suspend fun getAllWeighings(): List<Weighing> = weighingsDataSource.getAllWeighings()
    suspend fun upsert(weighing: Weighing) = weighingsDataSource.upsert(weighing)
    suspend fun getByDate(date: Instant): Weighing = weighingsDataSource.getByDate(date)
    suspend fun deleteByDate(date: Instant) = weighingsDataSource.deleteByDate(date)
}