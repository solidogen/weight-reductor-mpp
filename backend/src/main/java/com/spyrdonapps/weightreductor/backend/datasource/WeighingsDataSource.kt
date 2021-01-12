package com.spyrdonapps.weightreductor.backend.datasource

import com.spyrdonapps.weightreductor.backend.deletelater.Weighing
import kotlinx.datetime.Instant
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

interface WeighingsDataSource {
    suspend fun getAllWeighings(): List<Weighing>
    suspend fun upsert(weighing: Weighing)
    suspend fun getByDate(date: Instant)
    suspend fun deleteByDate(date: Instant)
}

class MongoWeighingsDataSource(
    private val coroutineDatabase: CoroutineDatabase
) : WeighingsDataSource {

    private val collection = coroutineDatabase.getCollection<Weighing>()

    override suspend fun getAllWeighings() = collection.find().toList()

    override suspend fun upsert(weighing: Weighing) {
        collection.insertOne(weighing)
    }

    override suspend fun getByDate(date: Instant) {
        collection.findOne(Weighing::date eq date)
    }

    override suspend fun deleteByDate(date: Instant) {
        collection.deleteOne(Weighing::date eq date)
    }
}