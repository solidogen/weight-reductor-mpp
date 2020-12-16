package com.spyrdonapps.weightreductor.android.di

import com.spyrdonapps.weightreductor.android.ui.features.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(sampleClientRepository = get(), logger = get()) }
}