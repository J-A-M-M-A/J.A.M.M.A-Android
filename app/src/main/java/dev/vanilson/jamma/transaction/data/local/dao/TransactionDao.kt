package dev.vanilson.jamma.transaction.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import dev.vanilson.jamma.transaction.data.local.entity.Category
import dev.vanilson.jamma.transaction.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TransactionDao {
    @Query(
        "SELECT * FROM `Transaction` " +
                "JOIN Category ON `Transaction`.category_id = Category.uid"
    )
    fun getAll(): Flow<Map<Transaction, Category>>

    @Query(
        "SELECT * FROM `Transaction` " +
                "JOIN Category ON `Transaction`.category_id = Category.uid " +
                "order by uid desc limit :x"
    )
    fun getLastX(x: Int): Flow<Map<Transaction, Category>>

    @Query(
        "SELECT * FROM `Transaction` " +
                "JOIN Category ON `Transaction`.category_id = Category.uid " +
                "WHERE `Transaction`.uid=:uid"
    )
    fun getById(uid: Int): Flow<Map<Transaction, Category>>

    @Query("SELECT COUNT(*) FROM `Transaction`")
    fun count(): Flow<Int>

    @Query(
        "SELECT * FROM `Transaction` " +
                "JOIN Category ON `Transaction`.category_id = Category.uid " +
                "WHERE paid_date IS NULL and due_date <= :tomorrow"
    )
    fun getOverdue(tomorrow: LocalDateTime): Flow<Map<Transaction, Category>>

    @Insert
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("DELETE FROM `Transaction`")
    fun deleteAll()

    @Upsert
    fun save(transaction: Transaction)

    @Query(
        "SELECT SUM(amount_in_cents) FROM `Transaction` " +
                "WHERE due_date >= :startDate AND due_date < :endDate AND income = 0"
    )
    fun getTotalExpenseByInterval(startDate: LocalDateTime, endDate: LocalDateTime): Flow<Long?>

}