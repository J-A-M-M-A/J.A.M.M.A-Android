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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime

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
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            transactionRepository.findLastX(15).onEach { transactions ->
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
            }.stateIn(viewModelScope)
//            .launchIn(viewModelScope)
        }
    }

    fun closeLightBox() {
        _state.update {
            it.copy(isAddingTransaction = false)
        }
    }

    fun openLightBox() {
        _state.update {
            it.copy(isAddingTransaction = true)
        }
    }

    fun saveTransaction(transactionUI: TransactionUI) {
        viewModelScope.launch(Dispatchers.IO) {
            // Save transaction to database
            Timber.d("Saving transaction: $transactionUI")
            transactionRepository.save(transactionUI.toTransaction())
        }
    }

    fun deleteTransaction(transactionUI: TransactionUI) {
        viewModelScope.launch(Dispatchers.IO) {
            // Delete transaction from database
            Timber.d("Deleting transaction: $transactionUI")
            transactionRepository.delete(transactionUI.toTransaction())
        }
    }

    fun togglePaidStatus(transactionUI: TransactionUI) {
        _state.update {
            it.copy(isLoading = true)
        }
        val updatedTransaction = if (transactionUI.isPaid) {
            transactionUI.copy(paidDateTime = null)
        } else {
            transactionUI.copy(paidDateTime = LocalDateTime.now())
        }
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("Toggling paid status for transaction: $updatedTransaction")
            transactionRepository.save(updatedTransaction.toTransaction())
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun markTransactionToDelete(transactionUI: TransactionUI) {
        _state.update {
            it.copy(transactionToDelete = transactionUI)
        }
    }

    fun deleteMarkedTransaction() {
        val transactionUI = state.value.transactionToDelete ?: return

        _state.update {
            it.copy(isLoading = true)
        }

        deleteTransaction(transactionUI)

        _state.update {
            it.copy(transactionToDelete = null, isLoading = false)
        }
    }
}