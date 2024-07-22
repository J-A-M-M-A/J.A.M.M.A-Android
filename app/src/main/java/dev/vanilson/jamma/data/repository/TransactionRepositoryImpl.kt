package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.db.AppDatabase
import dev.vanilson.jamma.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(private val appDatabase: AppDatabase) : TransactionRepository {
    override suspend fun save(transaction: Transaction) {
        appDatabase.transactionDao().save(transaction)
    }

    override suspend fun findById(id: Int): Transaction {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<List<Transaction>> {
        return appDatabase.transactionDao().getAll()
    }

    override fun findLastX(x: Int): Flow<List<Transaction>> {
        return appDatabase.transactionDao().getLastX(x)
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        appDatabase.transactionDao().deleteAll()
    }

}