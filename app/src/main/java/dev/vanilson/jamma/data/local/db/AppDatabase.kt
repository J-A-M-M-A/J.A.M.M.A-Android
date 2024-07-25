package dev.vanilson.jamma.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.vanilson.jamma.data.local.TransactionDao
import dev.vanilson.jamma.data.local.TransactionEntity
import dev.vanilson.jamma.data.utils.Converters

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

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