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

class TransactionEditViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        TransactionEditState(
            dueDate = LocalDateTime.now()
        )
    )

    val state = _state.onStart {
        loadCategories()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        TransactionEditState(
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
        _state.value = TransactionEditState(
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
            uid = state.value.transactionId, // Use 0 for new transactions
            title = state.value.description,
            amount = state.value.amountString.toFormattedMoney(),
            dueDateTime = state.value.dueDate,
            category = state.value.selectedCategoryUI ?: return,
            paidDateTime = state.value.paidDate,
            income = state.value.isIncome,
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

    fun amountBackspace() {
        val currentAmount = _state.value.amountString
        if (currentAmount.isNotEmpty()) {
            _state.value = _state.value.copy(
                amountString = currentAmount.dropLast(1).ifEmpty { "0.00" }
            )
        }
    }

    fun loadTransaction(transactionId: Int) {
        if (state.value.isEditing == true) {
            Timber.w(">>> Transaction is already being edited, skipping load.")
            return
        }
        Timber.i(">>> Loading transaction with ID: $transactionId")
        _state.value = _state.value.copy(
            isLoading = true,
            isEditing = true,
        )
        transactionRepository.findById(transactionId).onEach { transaction ->
            Timber.i(">>> Loaded transaction: $transaction")
            _state.value = _state.value.copy(
                transactionId = transaction.uid,
                description = transaction.title,
                amountString = transaction.amountInCents.toFormattedMoney().formatted,
                dueDate = transaction.dueDateTime,
                paidDate = transaction.paidDateTime,
                selectedCategoryUI = transaction.category.toCategoryUI(),
                isLoading = false,
                isIncome = transaction.income,
            )
        }.catch {
            Timber.e(it, ">>> Error loading transaction with ID: $transactionId")
            _state.value = _state.value.copy(
                isLoading = false,
            )
        }.launchIn(viewModelScope)
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

    fun toggleDatePickerVisibility() {
        _state.value = _state.value.copy(
            isDatePickerVisible = !_state.value.isDatePickerVisible
        )
    }

    fun setIsIncome(isIncome: Boolean) {
        _state.value = _state.value.copy(
            isIncome = isIncome
        )
    }

}