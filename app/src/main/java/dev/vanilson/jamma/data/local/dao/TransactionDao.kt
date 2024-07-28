package dev.vanilson.jamma.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import dev.vanilson.jamma.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `Transaction`")
    fun getAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM `Transaction` order by uid desc limit :x")
    fun getLastX(x: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM `Transaction` WHERE uid=:uid")
    fun getById(uid: Int): Flow<Transaction>

    @Query("SELECT COUNT(*) FROM `Transaction`")
    fun count(): Flow<Int>

    @Query("SELECT * FROM `Transaction` WHERE paid_date IS NULL and due_date <= :tomorrow")
    fun getOverdue(tomorrow: LocalDateTime): Flow<List<Transaction>>

    @Insert
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("DELETE FROM `Transaction`")
    fun deleteAll()

    @Upsert
    fun save(transaction: Transaction)

}