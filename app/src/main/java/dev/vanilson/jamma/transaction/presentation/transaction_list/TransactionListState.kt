package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.compose.runtime.Immutable
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI

@Immutable
data class TransactionListState(
    val transactions: List<TransactionUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val dayAmount: String = "$ 99.99",
    val weekAmount: String = "$ 999.99",
    val monthAmount: String = "$ 9 999.99",
    val totalBalance: String = "$ 999 999.99",
    val isAddingTransaction: Boolean = false,
    val transactionToDelete: TransactionUI? = null,
)