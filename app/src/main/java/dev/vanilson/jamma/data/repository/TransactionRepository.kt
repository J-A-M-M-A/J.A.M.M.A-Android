package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.entity.Transaction

interface TransactionRepository {
    fun save(transaction: Transaction)
    fun findById(id: Int): Transaction
    fun findAll(): List<Transaction>
    fun delete(id: Int)
    fun deleteAll()
//    fun count(): Int
}