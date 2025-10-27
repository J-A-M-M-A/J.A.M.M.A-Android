package dev.vanilson.jamma.transaction.domain

import java.time.LocalDateTime

data class Transaction(
    val uid: Int,
    val title: String,
    val amountInCents: Long,
    val dueDateTime: LocalDateTime = LocalDateTime.now(),
    val paidDateTime: LocalDateTime? = null,
    val income: Boolean = false,
    val category: Category,
    val walletId: Int, //todo: wallet?
    val recurrence: Recurrence = Recurrence.NONE,
    val parentId: Int? = null,
)