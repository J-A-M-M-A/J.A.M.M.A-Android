package dev.vanilson.jamma

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.vanilson.jamma.di.databaseModule
import dev.vanilson.jamma.di.repositoryModule
import dev.vanilson.jamma.di.useCaseModule
import dev.vanilson.jamma.di.viewModelModule
import dev.vanilson.jamma.transaction.data.worker.NotificationWorker
import dev.vanilson.jamma.utils.BILLS_DUE_CHANNEL_ID
import dev.vanilson.jamma.utils.SHARED_PREFERENCES_NAME
import dev.vanilson.jamma.utils.WORKER_CONFIG_KEY
import dev.vanilson.jamma.utils.WORKER_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
                useCaseModule,
            )
        }
        Timber.plant(Timber.DebugTree())
        createNotificationChannel()
        configureWorker(applicationContext)
    }

    private fun configureWorker(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

        sharedPreferences.getBoolean(WORKER_CONFIG_KEY, false).let { isConfigured ->
            if (isConfigured) {
                Timber.i(">>> Worker already configured")
                return
            }

            Timber.i(">>> Worker not configured, configuring...")
            val notificationWorker: PeriodicWorkRequest =
                PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORKER_NAME,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                notificationWorker
            )
            sharedPreferences.edit(commit = true) {
                Timber.i(">>> Worker configured")
                putBoolean(WORKER_CONFIG_KEY, true)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.bills_due_channel_name)
            val descriptionText = getString(R.string.bills_due_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(BILLS_DUE_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}