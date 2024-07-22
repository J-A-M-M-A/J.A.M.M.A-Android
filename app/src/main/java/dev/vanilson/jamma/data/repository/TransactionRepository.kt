package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.entity.Transaction

interface TransactionRepository {
    suspend fun save(transaction: Transaction)
    suspend fun findById(id: Int): Transaction
    suspend fun findAll(): List<Transaction>
    suspend fun delete(id: Int)
    suspend fun deleteAll()
//    fun count(): Int
}