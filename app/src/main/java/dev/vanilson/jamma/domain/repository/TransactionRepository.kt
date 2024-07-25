package dev.vanilson.jamma.domain.repository

import dev.vanilson.jamma.domain.model.Transaction
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