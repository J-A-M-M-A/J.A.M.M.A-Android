package dev.vanilson.jamma.domain.model

import java.time.LocalDateTime

data class Transaction(
    val uid: Int? = null,
    val title: String,
    val amountInCents: Long,
    val dueDateTime: LocalDateTime = LocalDateTime.now(),
    val paidDateTime: LocalDateTime? = null,
    val category: Category
)