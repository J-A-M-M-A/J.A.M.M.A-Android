package dev.vanilson.jamma.data.repository

import dev.vanilson.jamma.data.local.AppDatabase
import dev.vanilson.jamma.domain.model.Category
import dev.vanilson.jamma.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.vanilson.jamma.data.local.entity.Category as CategoryEntity

class CategoryRepositoryImpl(appDatabase: AppDatabase) : CategoryRepository {

    private val categoryDao = appDatabase.categoryDao()

    private fun entityListFlowToModelListFlow(entityListFlow: Flow<List<CategoryEntity>>): Flow<List<Category>> {
        return entityListFlow.map { entityList ->
            entityList.map { CategoryEntity.toModel(it) }
        }
    }

    override suspend fun save(category: Category) {
        categoryDao.save(CategoryEntity.fromModel(category))
    }

    override suspend fun findById(id: Int): Flow<Category> {
        return categoryDao.getById(id).map { CategoryEntity.toModel(it) }
    }

    override fun findAll(): Flow<List<Category>> {
        return entityListFlowToModelListFlow(categoryDao.getAll())
    }

}