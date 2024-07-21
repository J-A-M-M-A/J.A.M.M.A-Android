package dev.vanilson.jamma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import dev.vanilson.jamma.data.entity.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `Transaction`")
    fun getAll(): List<Transaction>

    @Query("SELECT * FROM `Transaction` WHERE uid=:uid")
    fun getById(uid: Int): Transaction

    @Insert
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Upsert
    fun save(transaction: Transaction)

}