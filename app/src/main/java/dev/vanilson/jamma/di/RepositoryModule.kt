package dev.vanilson.jamma.di

import dev.vanilson.jamma.transaction.data.repository.CategoryRepositoryImpl
import dev.vanilson.jamma.transaction.data.repository.TransactionRepositoryImpl
import dev.vanilson.jamma.transaction.data.repository.WalletRepositoryImpl
import dev.vanilson.jamma.transaction.domain.repository.CategoryRepository
import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.domain.repository.WalletRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { TransactionRepositoryImpl(get()) } bind TransactionRepository::class
    single { CategoryRepositoryImpl(get()) } bind CategoryRepository::class
    single { WalletRepositoryImpl(get()) } bind WalletRepository::class
}