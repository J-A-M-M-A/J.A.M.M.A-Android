package dev.vanilson.jamma.viewmodels

import androidx.lifecycle.ViewModel
import dev.vanilson.jamma.data.entity.Transaction
import dev.vanilson.jamma.data.repository.TransactionRepository
import timber.log.Timber

class MainViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    fun saveTransaction(transaction: Transaction) {
        // Save transaction to database
        Timber.d("Saving transaction: $transaction")
        transactionRepository.save(transaction)
    }
}