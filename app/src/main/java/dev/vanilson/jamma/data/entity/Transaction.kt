package dev.vanilson.jamma.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Long,
    @ColumnInfo(name = "due_date") val dueDateTime: LocalDateTime? = LocalDateTime.now(),
    @ColumnInfo(name = "paid_date") val paidDateTime: LocalDateTime? = null,
)