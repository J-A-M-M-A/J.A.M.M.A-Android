package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toTransaction
import dev.vanilson.jamma.transaction.presentation.models.toTransactionUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class TransactionListViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(TransactionListState())
    val state = _state
        .onStart { loadTransactions() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TransactionListState()
        )

    //todo debug only
    var clickedTimes = 0

    private fun loadTransactions() {
        _state.update {
            it.copy(isLoading = true)
        }
        transactionRepository.findLastX(5).onEach { transactions ->
            _state.update { state ->
                state.copy(
                    isLoading = false,
                    transactions = transactions.map { it.toTransactionUI() }
                )
            }
        }.catch {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = it.error
                )
            }
        }.launchIn(viewModelScope)
    }


//    val transactions = transactionRepository.findLastX(5).stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        emptyList()
//    )

    //    todo: move to proper place
    fun saveTransaction(transactionUI: TransactionUI) {
        viewModelScope.launch(Dispatchers.IO) {
            // Save transaction to database
            Timber.d("Saving transaction: $transactionUI")
            transactionRepository.save(transactionUI.toTransaction())
        }
    }

    //    todo: move to proper place
    fun deleteAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            // Delete all transactions from database
            transactionRepository.deleteAll()
        }
    }
}