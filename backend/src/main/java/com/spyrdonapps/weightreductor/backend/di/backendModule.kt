package com.spyrdonapps.weightreductor.backend.di

import com.spyrdonapps.weightreductor.backend.datasource.ExposedWeighingsDataSource
import com.spyrdonapps.weightreductor.backend.datasource.WeighingsDataSource
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import org.koin.dsl.module

val backendModule = module(createdAtStart = true) {
    single<WeighingsDataSource> {
        ExposedWeighingsDataSource()
    }
    single {
        WeighingsRepository(weighingsDataSource = get())
    }
}