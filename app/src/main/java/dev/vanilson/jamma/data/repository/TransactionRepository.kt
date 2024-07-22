package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun save(transaction: Transaction)
    suspend fun findById(id: Int): Transaction
    fun findAll(): Flow<List<Transaction>>
    fun findLastX(x: Int): Flow<List<Transaction>>
    suspend fun delete(id: Int)
    suspend fun deleteAll()
//    fun count(): Int
}