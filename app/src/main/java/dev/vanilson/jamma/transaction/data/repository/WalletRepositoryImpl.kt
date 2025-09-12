package dev.vanilson.jamma.transaction.data.repository

import dev.vanilson.jamma.transaction.data.local.AppDatabase
import dev.vanilson.jamma.transaction.data.local.entity.Wallet
import dev.vanilson.jamma.transaction.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class WalletRepositoryImpl(appDatabase: AppDatabase) : WalletRepository {
    private val walletDao = appDatabase.walletDao()

    override fun save(wallet: Wallet) {
        walletDao.save(wallet)
    }

    override fun findById(id: Int): Wallet? {
        return walletDao.getById(id)
    }

    override fun findAll(): Flow<List<Wallet>> {
        return walletDao.getAll()
    }
}