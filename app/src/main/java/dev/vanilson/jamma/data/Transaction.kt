package dev.vanilson.jamma.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Transaction(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Long,
    @ColumnInfo(name = "due_date") val dueDateTime: LocalDateTime,
    @ColumnInfo(name = "paid_date") val paidDateTime: LocalDateTime
)