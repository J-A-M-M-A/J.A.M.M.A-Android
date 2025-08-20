package dev.vanilson.jamma.di

import dev.vanilson.jamma.transaction.presentation.transaction_add.TransactionEditViewModel
import dev.vanilson.jamma.transaction.presentation.transaction_list.TransactionListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
//    single { TransactionListViewModel(get()) }
    viewModelOf(::TransactionListViewModel)
    viewModelOf(::TransactionEditViewModel)
}