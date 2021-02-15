package com.spyrdonapps.weightreductor.backend.datasource

import com.spyrdonapps.weightreductor.backend.deletelater.Weighing
import kotlinx.datetime.Instant

interface WeighingsDataSource {
    suspend fun getAllWeighings(): List<Weighing>
    suspend fun upsert(weighing: Weighing)
    suspend fun getByDate(date: Instant)
    suspend fun deleteByDate(date: Instant)
}

class PostgresWeighingsDataSource() : WeighingsDataSource {

    override suspend fun getAllWeighings(): List<Weighing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(weighing: Weighing) {
        TODO("Not yet implemented")
    }

    override suspend fun getByDate(date: Instant) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteByDate(date: Instant) {
        TODO("Not yet implemented")
    }
}

//class MongoWeighingsDataSource(
//    private val coroutineDatabase: CoroutineDatabase
//) : WeighingsDataSource {
//
//    private val collection = coroutineDatabase.getCollection<Weighing>()
//
//    override suspend fun getAllWeighings() = collection.find().toList()
//
//    override suspend fun upsert(weighing: Weighing) {
//        collection.insertOne(weighing)
//    }
//
//    override suspend fun getByDate(date: Instant) {
//        collection.findOne(Weighing::date eq date)
//    }
//
//    override suspend fun deleteByDate(date: Instant) {
//        collection.deleteOne(Weighing::date eq date)
//    }
//}