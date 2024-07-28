package dev.vanilson.jamma.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.vanilson.jamma.data.local.dao.CategoryDao
import dev.vanilson.jamma.data.local.dao.TransactionDao
import dev.vanilson.jamma.data.local.entity.Category
import dev.vanilson.jamma.data.local.entity.Transaction
import dev.vanilson.jamma.data.utils.Converters

@Database(entities = [Transaction::class, Category::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return db ?: synchronized(this) {
                buildDatabase(context).also { db = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "jamma-db")
                .build()
        }
    }
}