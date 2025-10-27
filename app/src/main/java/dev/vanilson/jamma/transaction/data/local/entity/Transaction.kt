package dev.vanilson.jamma.transaction.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.vanilson.jamma.transaction.domain.Recurrence
import java.time.LocalDateTime
import dev.vanilson.jamma.transaction.domain.Transaction as TransactionModel

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "amount_in_cents") val amountInCents: Long,
    @ColumnInfo(name = "due_date") val dueDateTime: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "paid_date") val paidDateTime: LocalDateTime? = null,
    @ColumnInfo(name = "income") val income: Boolean = false,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "wallet_id") val walletId: Int,
    @ColumnInfo(name = "recurrence") val recurrence: String,
    @ColumnInfo(name = "parent_id") val parentId: Int? = null,
) {
    companion object {
        fun fromModel(transaction: TransactionModel): Transaction {
            return Transaction(
                uid = transaction.uid,
                title = transaction.title,
                amountInCents = transaction.amountInCents,
                dueDateTime = transaction.dueDateTime,
                paidDateTime = transaction.paidDateTime,
                income = transaction.income,
                categoryId = transaction.category.uid,
                walletId = transaction.walletId,
                recurrence = transaction.recurrence.displayName,
                parentId = transaction.parentId,
            )
        }

        fun toModel(transaction: Transaction, category: Category): TransactionModel {
            return TransactionModel(
                uid = transaction.uid,
                title = transaction.title,
                amountInCents = transaction.amountInCents,
                dueDateTime = transaction.dueDateTime,
                paidDateTime = transaction.paidDateTime,
                income = transaction.income,
                category = Category.toModel(category),
                walletId = transaction.walletId,
                recurrence = Recurrence.fromDisplayName(transaction.recurrence),
                parentId = transaction.parentId,
            )
        }
    }
}