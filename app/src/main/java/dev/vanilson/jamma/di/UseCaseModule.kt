package dev.vanilson.jamma.di

import dev.vanilson.jamma.transaction.domain.usecase.DeleteTransactionAndUpdateBalanceUseCase
import dev.vanilson.jamma.transaction.domain.usecase.SaveTransactionAndUpdateBalanceUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { SaveTransactionAndUpdateBalanceUseCase(get(), get()) }
    factory { DeleteTransactionAndUpdateBalanceUseCase(get(), get()) }
}