package dev.vanilson.jamma.transaction.domain

import java.time.LocalDateTime

data class Transaction(
    val uid: Int? = null,
    val title: String,
    val amountInCents: Long,
    val dueDateTime: LocalDateTime = LocalDateTime.now(),
    val paidDateTime: LocalDateTime? = null,
    val category: Category,
    val income: Boolean = false,
)