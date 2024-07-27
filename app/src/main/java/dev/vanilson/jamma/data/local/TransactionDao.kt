package dev.vanilson.jamma.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TransactionDao {
    @Query("SELECT * FROM TransactionEntity")
    fun getAll(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity order by uid desc limit :x")
    fun getLastX(x: Int): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity WHERE uid=:uid")
    fun getById(uid: Int): Flow<TransactionEntity>

    @Query("SELECT COUNT(*) FROM TransactionEntity")
    fun count(): Flow<Int>

    @Query("SELECT * FROM TransactionEntity WHERE paid_date IS NULL and due_date <= :tomorrow")
    fun getOverdue(tomorrow: LocalDateTime): Flow<List<TransactionEntity>>

    @Insert
    fun insertAll(vararg transactionEntities: TransactionEntity)

    @Delete
    fun delete(transactionEntity: TransactionEntity)

    @Query("DELETE FROM TransactionEntity")
    fun deleteAll()

    @Upsert
    fun save(transactionEntity: TransactionEntity)

}