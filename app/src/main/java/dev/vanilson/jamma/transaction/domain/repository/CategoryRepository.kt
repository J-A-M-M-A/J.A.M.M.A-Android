package dev.vanilson.jamma.transaction.domain.repository

import dev.vanilson.jamma.transaction.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun save(category: Category)
    suspend fun findById(id: Int): Flow<Category>
    fun findAll(): Flow<List<Category>>
}