package dev.vanilson.jamma.transaction.data.local

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.vanilson.jamma.R
import dev.vanilson.jamma.transaction.data.local.entity.Category
import dev.vanilson.jamma.transaction.data.local.entity.Wallet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import timber.log.Timber

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulateCategories(context)
            prePopulateWallets(context)
        }
    }

    private fun prePopulateCategories(context: Context) {
        try {
            val categoryDao = AppDatabase.getInstance(context).categoryDao()
            val categories =
                context.resources.openRawResource(R.raw.categories).bufferedReader().use {
                    JSONArray(it.readText())
                }

            categories.takeIf { it.length() > 0 }?.let {
                for (i in 0 until it.length()) {
                    val category = it.getJSONObject(i)
                    categoryDao.save(
                        Category(
                            uid = category.getInt("uid"),
                            name = category.getString("name"),
                            icon = category.getString("icon"),
                        )
                    )
                }
                Timber.i("Categories prepopulated")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun prePopulateWallets(context: Context) {
        try {
            val walletDao = AppDatabase.getInstance(context).walletDao()
            val wallets = listOf(
                Wallet(
                    name = "Cash",
                    balance = 0.00,
                ),
            )
            wallets.forEach {
                walletDao.save(it)
            }
            Timber.i("Wallets prepopulated")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}