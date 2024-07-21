package dev.vanilson.jamma.di

import dev.vanilson.jamma.viewmodels.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel(get()) }
}