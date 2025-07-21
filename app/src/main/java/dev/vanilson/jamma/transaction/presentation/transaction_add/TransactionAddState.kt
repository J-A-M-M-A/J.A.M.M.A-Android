package dev.vanilson.jamma.transaction.presentation.transaction_add

import androidx.compose.runtime.Immutable
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import java.time.LocalDateTime

@Immutable
data class TransactionAddState(
    val categoryUI: CategoryUI? = null,
    val amountString: String = "0.00",
    val description: String = "",
    val dueDate: LocalDateTime,
    val paidDate: LocalDateTime? = null,
) {
    val isValid: Boolean
        get() = amountString.isNotBlank()
                && description.isNotBlank()
                && categoryUI != null
}