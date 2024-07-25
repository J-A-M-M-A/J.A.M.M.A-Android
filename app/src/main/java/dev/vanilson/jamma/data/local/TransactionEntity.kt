package dev.vanilson.jamma.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.vanilson.jamma.domain.model.Transaction
import java.time.LocalDateTime

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Long,
    @ColumnInfo(name = "due_date") val dueDateTime: LocalDateTime? = LocalDateTime.now(),
    @ColumnInfo(name = "paid_date") val paidDateTime: LocalDateTime? = null,
) {
    companion object {
        fun fromTransaction(transaction: Transaction): TransactionEntity {
            return TransactionEntity(
                title = transaction.title,
                amountInCents = transaction.amountInCents,
                dueDateTime = transaction.dueDateTime,
                paidDateTime = transaction.paidDateTime
            )
        }

        fun toTransaction(transactionEntity: TransactionEntity): Transaction {
            return Transaction(
                title = transactionEntity.title,
                amountInCents = transactionEntity.amountInCents,
                dueDateTime = transactionEntity.dueDateTime,
                paidDateTime = transactionEntity.paidDateTime
            )
        }
    }
}