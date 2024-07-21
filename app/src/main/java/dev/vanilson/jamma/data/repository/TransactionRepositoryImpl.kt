package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.db.AppDatabase
import dev.vanilson.jamma.data.entity.Transaction

class TransactionRepositoryImpl(private val appDatabase: AppDatabase) : TransactionRepository {
    override fun save(transaction: Transaction) {
        appDatabase.transactionDao().save(transaction)
    }

    override fun findById(id: Int): Transaction {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Transaction> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

}