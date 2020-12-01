package com.spyrdonapps.common.di

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.repository.SampleClientRepository
import com.spyrdonapps.common.repository.getLogger
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule)
}

// called by iOS/frontend etc
fun initKoin() = initKoin{}

val commonModule = module {
    single { Kermit(getLogger()) }
    single { SampleClientRepository() }
}