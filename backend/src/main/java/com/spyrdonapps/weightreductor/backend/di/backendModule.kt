package com.spyrdonapps.weightreductor.backend.di

import com.mongodb.ConnectionString
import com.spyrdonapps.weightreductor.backend.datasource.MongoWeighingsDataSource
import com.spyrdonapps.weightreductor.backend.datasource.WeighingsDataSource
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val backendModule = module(createdAtStart = true) {

    single<CoroutineDatabase> {
        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
            ConnectionString("$it?retryWrites=false&w=majority")
        }
        val client = if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient().coroutine
        val database = client.getDatabase(connectionString?.database ?: "wr-database")
        database
    }

    single<WeighingsDataSource> {
        MongoWeighingsDataSource(coroutineDatabase = get())
    }

    single {
        WeighingsRepository(weighingsDataSource = get())
    }
}