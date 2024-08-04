package dev.vanilson.jamma

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.vanilson.jamma.data.worker.NotificationWorker
import dev.vanilson.jamma.di.databaseModule
import dev.vanilson.jamma.di.repositoryModule
import dev.vanilson.jamma.di.viewModelModule
import dev.vanilson.jamma.utils.BILLS_DUE_CHANNEL_ID
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
            )
        }
        Timber.plant(Timber.DebugTree())
        createNotificationChannel()
        configureWorker(applicationContext)
    }

    private fun configureWorker(context: Context) {
        val notificationWorker: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sendNotification",
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorker
        )
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
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}