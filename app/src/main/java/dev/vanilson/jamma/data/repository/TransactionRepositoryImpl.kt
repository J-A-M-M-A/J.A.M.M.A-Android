package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.local.TransactionEntity
import dev.vanilson.jamma.data.local.db.AppDatabase
import dev.vanilson.jamma.domain.model.Transaction
import dev.vanilson.jamma.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val appDatabase: AppDatabase) : TransactionRepository {
    override suspend fun save(transaction: Transaction) {
        appDatabase.transactionDao().save(TransactionEntity.fromTransaction(transaction))
    }

    override suspend fun findById(id: Int): Transaction {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<List<Transaction>> {
        val allTransactionEntities = appDatabase.transactionDao().getAll()
        return allTransactionEntities.map { entityList ->
            entityList.map { TransactionEntity.toTransaction(it) }
        }
    }

    override fun findLastX(x: Int): Flow<List<Transaction>> {
        val transactionEntities = appDatabase.transactionDao().getAll()
        return transactionEntities.map { entityList ->
            entityList.map { TransactionEntity.toTransaction(it) }
        }
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        appDatabase.transactionDao().deleteAll()
    }

}