package dev.vanilson.jamma.domain.repository

import dev.vanilson.jamma.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun save(category: Category)
    suspend fun findById(id: Int): Flow<Category>
    fun findAll(): Flow<List<Category>>
}