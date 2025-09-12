package dev.vanilson.jamma.transaction.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.vanilson.jamma.transaction.data.local.entity.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Query("SELECT * FROM Wallet")
    fun getAll(): Flow<List<Wallet>>

    @Query("SELECT * FROM Wallet WHERE uid=:uid")
    fun getById(uid: Int): Wallet?

    @Upsert
    fun save(wallet: Wallet)

}