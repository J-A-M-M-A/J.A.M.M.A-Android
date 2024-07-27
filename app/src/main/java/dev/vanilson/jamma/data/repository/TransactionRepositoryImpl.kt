package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.local.TransactionEntity
import dev.vanilson.jamma.data.local.db.AppDatabase
import dev.vanilson.jamma.domain.model.Transaction
import dev.vanilson.jamma.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class TransactionRepositoryImpl(appDatabase: AppDatabase) : TransactionRepository {

    private val transactionDao = appDatabase.transactionDao()

    override suspend fun save(transaction: Transaction) {
        transactionDao.save(TransactionEntity.fromTransaction(transaction))
    }

    override suspend fun findById(id: Int): Flow<Transaction> {
        return transactionDao.getById(id).map { TransactionEntity.toTransaction(it) }
    }

    override fun findAll(): Flow<List<Transaction>> {
        return entityListFlowToModelListFlow(transactionDao.getAll())
    }

    override fun findLastX(x: Int): Flow<List<Transaction>> {
        return entityListFlowToModelListFlow(transactionDao.getLastX(x))
    }

    override suspend fun delete(transaction: Transaction) {
        transactionDao.delete(TransactionEntity.fromTransaction(transaction))
    }

    override suspend fun deleteAll() {
        transactionDao.deleteAll()
    }

    override fun count(): Flow<Int> {
        return transactionDao.count()
    }

    override fun findOverdue(): Flow<List<Transaction>> {
        val tomorrow = LocalDateTime.now().plusDays(1)
        return entityListFlowToModelListFlow(transactionDao.getOverdue(tomorrow))
    }

    private fun entityListFlowToModelListFlow(entityListFlow: Flow<List<TransactionEntity>>): Flow<List<Transaction>> {
        return entityListFlow.map { entityList ->
            entityList.map { TransactionEntity.toTransaction(it) }
        }
    }

}