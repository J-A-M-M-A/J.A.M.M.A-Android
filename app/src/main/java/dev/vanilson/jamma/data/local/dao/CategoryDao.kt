package dev.vanilson.jamma.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.vanilson.jamma.data.local.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM Category WHERE uid=:uid")
    fun getById(uid: Int): Flow<Category>

    @Upsert
    fun save(category: Category)

}