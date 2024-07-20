package dev.vanilson.amma.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface TransactionDao {
    @Query("SELECT * FROM `Transaction`")
    fun getAll(): List<Transaction>

    @Query("SELECT * FROM `Transaction` WHERE uid=:uid")
    fun getById(uid: Int): Transaction

    @Insert
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)

}