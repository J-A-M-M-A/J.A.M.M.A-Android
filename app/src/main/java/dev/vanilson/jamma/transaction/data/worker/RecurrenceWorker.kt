package dev.vanilson.jamma.transaction.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.vanilson.jamma.transaction.domain.Recurrence
import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class RecurrenceWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val transactionRepository: TransactionRepository by inject(TransactionRepository::class.java)

    override suspend fun doWork(): Result {
        Timber.i(">>> Performing RecurrenceWorker task...")

        val transactionId = inputData.getInt("transaction_id", -1)

        if (transactionId == -1) {
            Timber.e(">>> Transaction ID not provided")
            return Result.failure()
        }

        val transaction = transactionRepository.findById(transactionId)

        if (transaction.recurrence != Recurrence.None) {
            val nextTransaction = transaction.copy(
                uid = 0, // Set to 0 to auto-generate a new ID
                dueDateTime = when (transaction.recurrence) {
                    Recurrence.Daily -> transaction.dueDateTime.plusDays(1)
                    Recurrence.Weekly -> transaction.dueDateTime.plusWeeks(1)
                    Recurrence.Monthly -> transaction.dueDateTime.plusMonths(1)
                    Recurrence.Yearly -> transaction.dueDateTime.plusYears(1)
                    Recurrence.None -> transaction.dueDateTime // Should not happen
                },
                paidDateTime = null
            )
            transactionRepository.save(nextTransaction)
            Timber.i(">>> Created next transaction for recurring transaction $transactionId")
        }

        return Result.success()
    }
}