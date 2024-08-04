package dev.vanilson.jamma.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import dev.vanilson.jamma.domain.model.Transaction as TransactionModel

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Long,
    @ColumnInfo(name = "due_date") val dueDateTime: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "paid_date") val paidDateTime: LocalDateTime? = null,
    @ColumnInfo(name = "category_id") val categoryId: Int? = null
) {
    companion object {
        fun fromModel(transaction: TransactionModel): Transaction {
            return Transaction(
                uid = transaction.uid,
                title = transaction.title,
                amountInCents = transaction.amountInCents,
                dueDateTime = transaction.dueDateTime,
                paidDateTime = transaction.paidDateTime,
                categoryId = transaction.category.uid
            )
        }

        fun toModel(transaction: Transaction, category: Category): TransactionModel {
            return TransactionModel(
                uid = transaction.uid,
                title = transaction.title,
                amountInCents = transaction.amountInCents,
                dueDateTime = transaction.dueDateTime,
                paidDateTime = transaction.paidDateTime,
                category = Category.toModel(category)
            )
        }
    }
}