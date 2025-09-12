package dev.vanilson.jamma.transaction.presentation.models

import dev.vanilson.jamma.transaction.domain.Transaction
import dev.vanilson.jamma.utils.FormatedMoney
import dev.vanilson.jamma.utils.toFormattedMoney
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TransactionUI(
    val uid: Int,
    val title: String,
    val amount: FormatedMoney,
    val dueDateTime: LocalDateTime,
    val paidDateTime: LocalDateTime? = null,
    val category: CategoryUI,
    val income: Boolean = false,
    val walletId: Int,
) {
    val isPaid: Boolean
        get() = paidDateTime != null

    val isOverdue: Boolean
        get() = dueDateTime.isBefore(LocalDateTime.now()) && !isPaid

    val formattedDueDate: String
        get() = dueDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}



fun Transaction.toTransactionUI(): TransactionUI {
    return TransactionUI(
        uid = uid,
        title = title,
        amount = amountInCents.toFormattedMoney(),
        dueDateTime = dueDateTime,
        paidDateTime = paidDateTime,
        category = category.toCategoryUI(),
        income = income,
        walletId = walletId
    )
}

fun TransactionUI.toTransaction(): Transaction {
    return Transaction(
        uid = uid,
        title = title,
        amountInCents = amount.amountInCents,
        dueDateTime = dueDateTime,
        paidDateTime = paidDateTime,
        category = category.toCategory(),
        income = income,
        walletId = walletId
    )

}