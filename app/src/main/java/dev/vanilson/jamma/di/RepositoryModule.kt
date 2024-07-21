package dev.vanilson.jamma.di

import dev.vanilson.jamma.data.repository.TransactionRepository
import dev.vanilson.jamma.data.repository.TransactionRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { TransactionRepositoryImpl(get()) } bind TransactionRepository::class
}