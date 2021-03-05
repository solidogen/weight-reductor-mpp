package com.spyrdonapps.weightreductor.backend.di

import com.spyrdonapps.weightreductor.backend.datasource.ExposedWeighingsDataSource
import com.spyrdonapps.weightreductor.backend.datasource.WeighingsDataSource
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import org.koin.dsl.module

val backendModule = module(createdAtStart = true) {

    // fixme remove after analysis what I need now
//    single<CoroutineDatabase> {
//        val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
//            ConnectionString("$it?retryWrites=false&w=majority")
//        }
//        val client = if (connectionString != null) KMongo.createClient(connectionString).coroutine else KMongo.createClient().coroutine
//        val database = client.getDatabase(connectionString?.database ?: "wr-database")
//        database
//    }

    single<WeighingsDataSource> {
        ExposedWeighingsDataSource()
    }

    single {
        WeighingsRepository(weighingsDataSource = get())
    }
}