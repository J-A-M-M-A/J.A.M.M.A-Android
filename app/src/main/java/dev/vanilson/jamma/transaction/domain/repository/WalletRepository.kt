package dev.vanilson.jamma.transaction.domain.repository

import dev.vanilson.jamma.transaction.data.local.entity.Wallet
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun save(wallet: Wallet)
    fun findById(id: Int): Flow<Wallet>
    fun findAll(): Flow<List<Wallet>>
}