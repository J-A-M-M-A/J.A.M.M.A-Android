package dev.vanilson.jamma.transaction.presentation.models

import dev.vanilson.jamma.transaction.domain.Transaction
import java.time.LocalDateTime
import java.util.Locale

data class TransactionUI(
    val uid: Int? = null,
    val title: String,
    val amount: FormatedMoney,
    val dueDateTime: LocalDateTime,
    val paidDateTime: LocalDateTime? = null,
    val category: CategoryUI
)

data class FormatedMoney(
    val amountInCents: Long,
    val formatted: String
)

fun Long.toFormattedMoney(): FormatedMoney {
    val formatted = String.format(Locale.getDefault(), "%.2f", this / 100.0)
    return FormatedMoney(this, formatted)
}

fun String.toFormattedMoney(): FormatedMoney {
    val amountInCents = (this.replace(".", "").replace(",", "").toLong())
    return amountInCents.toFormattedMoney()
}

fun Transaction.toTransactionUI(): TransactionUI {
    return TransactionUI(
        uid = uid,
        title = title,
        amount = amountInCents.toFormattedMoney(),
        dueDateTime = dueDateTime,
        paidDateTime = paidDateTime,
        category = category.toCategoryUI()
    )
}

fun TransactionUI.toTransaction(): Transaction {
    return Transaction(
        uid = uid,
        title = title,
        amountInCents = amount.amountInCents,
        dueDateTime = dueDateTime,
        paidDateTime = paidDateTime,
        category = category.toCategory()
    )

}