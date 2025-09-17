package dev.vanilson.jamma.transaction.domain.repository

import dev.vanilson.jamma.transaction.domain.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransactionRepository {
    suspend fun save(transaction: Transaction)
    fun findById(id: Int): Transaction
    fun findAll(): Flow<List<Transaction>>
    fun findLastX(x: Int): Flow<List<Transaction>>
    suspend fun delete(transaction: Transaction)
    suspend fun deleteAll()
    fun count(): Flow<Int>
    fun findOverdue(): Flow<List<Transaction>>
    fun getTotalExpenseByInterval(startDate: LocalDateTime, endDate: LocalDateTime): Flow<Long?>
}