package dev.vanilson.jamma.transaction.presentation.transaction_list

import androidx.compose.runtime.Immutable
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI

@Immutable
data class TransactionListState(
    val transactions: List<TransactionUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)