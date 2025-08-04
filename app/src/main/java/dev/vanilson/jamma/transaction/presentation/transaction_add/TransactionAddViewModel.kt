package dev.vanilson.jamma.transaction.presentation.transaction_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vanilson.jamma.transaction.domain.repository.CategoryRepository
import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.presentation.models.CategoryUI
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toCategoryUI
import dev.vanilson.jamma.transaction.presentation.models.toFormattedMoney
import dev.vanilson.jamma.transaction.presentation.models.toTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime

class TransactionAddViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        TransactionAddState(
            dueDate = LocalDateTime.now()
        )
    )

    val state = _state.onStart {
        loadCategories()
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

    fun resetAmountString() {
        _state.value = _state.value.copy(amountString = "0.00")
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

    fun saveTransaction() {
        if (!state.value.isValid) {
            _state.value = _state.value.copy(
                success = false,
                error = "Please fill in all fields correctly." // todo: use string resource, show error in UI
            )
            return
        }
        val transactionUI = TransactionUI(
            title = state.value.description,
            amount = state.value.amountString.toFormattedMoney(),
            dueDateTime = state.value.dueDate,
            category = state.value.selectedCategoryUI ?: return,
            paidDateTime = state.value.paidDate,
        )
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("Saving transaction: $transactionUI")
            transactionRepository.save(transactionUI.toTransaction())
            _state.value = _state.value.copy(
                success = true,
            )
            Timber.i(">>> Transaction saved successfully, navigating back to transaction list")
        }
    }

    private fun loadCategories() {
        Timber.i(">>> Loading categories for transaction add screen")
        _state.value = _state.value.copy(
            isLoading = true,
        )
        categoryRepository.findAll().onEach { categories ->
            Timber.i(">>> Loaded ${categories.size} categories for transaction add screen")
            _state.value = _state.value.copy(
                categories = categories.map { it.toCategoryUI() },
                isLoading = false,
            )
        }.catch {
            Timber.e(it, ">>> Error loading categories for transaction add screen")
            _state.value = _state.value.copy(
                isLoading = false,
            )
        }.launchIn(viewModelScope)
    }


}