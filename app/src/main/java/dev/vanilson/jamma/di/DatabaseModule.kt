package dev.vanilson.jamma.di

import dev.vanilson.jamma.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
}
