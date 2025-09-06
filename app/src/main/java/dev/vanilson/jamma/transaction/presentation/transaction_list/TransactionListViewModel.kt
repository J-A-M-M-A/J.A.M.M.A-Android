package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.domain.repository.WalletRepository
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toFormattedMoney
import dev.vanilson.jamma.transaction.presentation.models.toTransaction
import dev.vanilson.jamma.transaction.presentation.models.toTransactionUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDateTime

class TransactionListViewModel(
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(TransactionListState())
    val state = _state
        .onStart {
            loadTransactions()
            loadSums()
            loadBalance()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TransactionListState()
        )

    //todo debug only
    var clickedTimes = 0

    private fun loadBalance() {
        viewModelScope.launch {
            walletRepository.findAll().firstOrNull()?.firstOrNull()?.let { wallet ->
                val balance = "$ " + wallet.balanceInCents.toFormattedMoney().formatted
                Timber.d(">>> Wallet balance: $balance")
                _state.update {
                    it.copy(
                        totalBalance = balance
                    )
                }
            }
        }
    }

    private fun loadSums() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        loadSumForInterval(
            now,
            now.plusDays(1),
            DAY
        )
        val currentWeekRange = getCurrentWeekRange()
        loadSumForInterval(
            currentWeekRange.first,
            currentWeekRange.second,
            WEEK
        )
        loadSumForInterval(
            now.withDayOfMonth(1),
            now.withDayOfMonth(1).plusMonths(1).minusSeconds(1),
            MONTH
        )
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun getCurrentWeekRange(): Pair<LocalDateTime, LocalDateTime> {
        val now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        when (now.dayOfWeek) {
            DayOfWeek.MONDAY -> {
                return Pair(now, now.plusDays(6).withHour(23).withMinute(59).withSecond(59))
            }

            DayOfWeek.TUESDAY -> {
                return Pair(
                    now.minusDays(1),
                    now.plusDays(5).withHour(23).withMinute(59).withSecond(59)
                )
            }

            DayOfWeek.WEDNESDAY -> {
                return Pair(
                    now.minusDays(2),
                    now.plusDays(4).withHour(23).withMinute(59).withSecond(59)
                )
            }

            DayOfWeek.THURSDAY -> {
                return Pair(
                    now.minusDays(3),
                    now.plusDays(3).withHour(23).withMinute(59).withSecond(59)
                )
            }

            DayOfWeek.FRIDAY -> {
                return Pair(
                    now.minusDays(4),
                    now.plusDays(2).withHour(23).withMinute(59).withSecond(59)
                )
            }

            DayOfWeek.SATURDAY -> {
                return Pair(
                    now.minusDays(5),
                    now.plusDays(1).withHour(23).withMinute(59).withSecond(59)
                )
            }

            DayOfWeek.SUNDAY -> {
                return Pair(now.minusDays(6), now.withHour(23).withMinute(59).withSecond(59))
            }
        }
    }

    private fun loadSumForInterval(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        interval: String
    ) {
        viewModelScope.launch {
            transactionRepository.getTotalExpenseByInterval(startDate, endDate)
                .onEach { totalInCents ->
                    val formattedMoney = "$ " + (totalInCents ?: 0L).toFormattedMoney().formatted
                    Timber.d(">>> Total expense from $startDate to $endDate: $formattedMoney")
                    _state.update {
                        when (interval) {
                            DAY -> it.copy(
                                dayAmount = formattedMoney
                            )

                            WEEK -> it.copy(
                                weekAmount = formattedMoney
                            )

                            else -> it.copy(
                                monthAmount = formattedMoney
                            )
                        }
                    }
                }.catch { error ->
                    Timber.e(error, "Error fetching total expense from $startDate to $endDate")
                }.stateIn(viewModelScope)
        }
    }

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

    companion object {
        const val DAY = "day"
        const val WEEK = "week"
        const val MONTH = "month"
    }
}