package dev.vanilson.jamma.transaction.presentation.transaction_add

import androidx.compose.runtime.Immutable
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Immutable
data class TransactionEditState(
    val selectedCategoryUI: CategoryUI? = null,
    val amountString: String = "0.00",
    val description: String = "",
    val dueDate: LocalDateTime,
    val paidDate: LocalDateTime? = null,
    val categories: List<CategoryUI> = emptyList(),
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val isEditing: Boolean? = false,
    val transactionId: Int? = null,
    val isDatePickerVisible: Boolean = false,
    val isIncome: Boolean = false,
) {
    val isValid: Boolean
        get() = amountString.isNotBlank()
                && description.isNotBlank()
                && selectedCategoryUI != null

    val dueDateFormatted: String
        get() = dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}