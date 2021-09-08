package com.spyrdonapps.weightreductor.backend.di

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.getLogger
import com.spyrdonapps.weightreductor.backend.datasource.ExposedUsersDataSource
import com.spyrdonapps.weightreductor.backend.datasource.ExposedWeighingsDataSource
import com.spyrdonapps.weightreductor.backend.datasource.UsersDataSource
import com.spyrdonapps.weightreductor.backend.datasource.WeighingsDataSource
import com.spyrdonapps.weightreductor.backend.repository.UsersRepository
import com.spyrdonapps.weightreductor.backend.repository.WeighingsRepository
import com.spyrdonapps.weightreductor.backend.util.utils.BCryptPasswordHasher
import com.spyrdonapps.weightreductor.backend.util.utils.PasswordHasher
import org.koin.dsl.module

val backendModule = module(createdAtStart = true) {
    single<WeighingsDataSource> {
        ExposedWeighingsDataSource()
    }
    single {
        WeighingsRepository(weighingsDataSource = get())
    }
    single {
        Kermit(getLogger())
    }
    single<PasswordHasher> {
        BCryptPasswordHasher(kermit = get())
    }
    single<UsersDataSource> {
        ExposedUsersDataSource(passwordHasher = get())
    }
    single {
        UsersRepository(usersDataSource = get())
    }
}