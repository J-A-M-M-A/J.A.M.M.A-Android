package dev.vanilson.jamma.di

import dev.vanilson.jamma.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
//    single { MainViewModel(get()) }
    viewModelOf(::MainViewModel)
}