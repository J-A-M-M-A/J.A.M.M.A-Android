package dev.vanilson.jamma.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.jamma.data.entity.Transaction
import dev.vanilson.jamma.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    val transactions = transactionRepository.findLastX(5).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            // Save transaction to database
            Timber.d("Saving transaction: $transaction")
            transactionRepository.save(transaction)
        }
    }

    fun deleteAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            // Delete all transactions from database
            transactionRepository.deleteAll()
        }
    }
}