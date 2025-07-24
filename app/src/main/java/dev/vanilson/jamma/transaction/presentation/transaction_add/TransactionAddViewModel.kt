package dev.vanilson.jamma.transaction.presentation.transaction_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

class TransactionAddViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        TransactionAddState(
            dueDate = LocalDateTime.now()
        )
    )

    val state = _state.onStart {
        //todo loadCategories()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        TransactionAddState(
            dueDate = LocalDateTime.now()
        )
    )

    fun updateCategory(categoryUI: CategoryUI) {
        _state.value = _state.value.copy(selectedCategoryUI = categoryUI)
    }

    fun updateAmountString(amountString: String) {
        val currentAmount = _state.value.amountString
        if (currentAmount == "0.00") {
            // Replace the initial "0.00" with the new input
            _state.value = _state.value.copy(amountString = amountString)
            return
        }
        _state.value = _state.value.copy(amountString = currentAmount + amountString)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun updateDueDate(dueDate: LocalDateTime) {
        _state.value = _state.value.copy(dueDate = dueDate)
    }

    fun updatePaidDate(isPaid: Boolean) {
        _state.value = isPaid.let {
            if (it) {
                _state.value.copy(paidDate = LocalDateTime.now())
            } else {
                _state.value.copy(paidDate = null)
            }
        }
    }

    fun resetState() {
        _state.value = TransactionAddState(
            dueDate = LocalDateTime.now()
        )
    }


}