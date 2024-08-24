package dev.vanilson.jamma.data.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.vanilson.jamma.MainActivity
import dev.vanilson.jamma.R
import dev.vanilson.jamma.domain.repository.TransactionRepository
import dev.vanilson.jamma.utils.BILLS_DUE_CHANNEL_ID
import kotlinx.coroutines.flow.first
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val transactionRepository: TransactionRepository by inject(TransactionRepository::class.java)

    override suspend fun doWork(): Result {
        Timber.i("Performing NotificationWorker task...")
        val notifyIntent: Intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val transactions = transactionRepository.findOverdue().first()

        Timber.d("Transactions: $transactions")
        if (transactions.isEmpty()) {
            return Result.failure()
        }

        val notifyPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(
            applicationContext,
            BILLS_DUE_CHANNEL_ID
        ).setContentTitle("Bills Due")
            .setContentText("You have ${transactions.size} bills due")
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(applicationContext)) {
            try {
                notify(1, builder.build())
                Timber.d("Notification sent")
            } catch (e: SecurityException) {
                Timber.e(e)
            }
        }

        return Result.success()

    }

}