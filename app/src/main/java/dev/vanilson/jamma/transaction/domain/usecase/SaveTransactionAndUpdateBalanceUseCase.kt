package dev.vanilson.jamma.transaction.domain.usecase

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dev.vanilson.jamma.transaction.data.worker.RecurrenceWorker
import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.domain.repository.WalletRepository
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toTransaction
import timber.log.Timber

class SaveTransactionAndUpdateBalanceUseCase(
    private val context: Context,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(transactionUI: TransactionUI) {
        updateBalance(transactionUI)

        // Save the transaction
        Timber.d(">>> Saving transaction: $transactionUI")
        transactionRepository.save(transactionUI.toTransaction())

        if (transactionUI.isPaid) {
            val workRequest = OneTimeWorkRequestBuilder<RecurrenceWorker>()
                .setInputData(workDataOf("transaction_id" to transactionUI.uid))
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

    fun updateBalance(transactionUI: TransactionUI) {

        var amountToDeduct = 0L
        var amountToAdd = 0L

        if (transactionUI.uid != 0) {
            Timber.i(">>> Updating existing transaction with ID: ${transactionUI.uid}")
            transactionRepository.findById(transactionUI.uid).let {
                if (it.amountInCents != transactionUI.amount.amountInCents) {
                    Timber.i(">>> Transaction amount has changed from ${it.amountInCents} to ${transactionUI.amount.amountInCents}")
                    //amount changed, need to adjust balance
                    if (transactionUI.income) {
                        //was income, still is income, just update the difference
                        amountToAdd = transactionUI.amount.amountInCents - it.amountInCents
                    } else {
                        //was expense, still is expense, just update the difference
                        amountToDeduct = transactionUI.amount.amountInCents - it.amountInCents
                    }
                    Timber.i(">>> Adjusting balance due to amount change. Deducting: $amountToDeduct, Adding: $amountToAdd")
                }

                val wasPaid = it.paidDateTime != null
                if (wasPaid && transactionUI.isPaid.not()) {
                    //need to charge the balance
                    amountToAdd = amountToAdd + transactionUI.amount.amountInCents
                } else if (wasPaid.not() && transactionUI.isPaid) {
                    //need to discharge the balance
                    amountToDeduct = transactionUI.amount.amountInCents
                } else {
                    Timber.i(">>> Transaction payment status remains unchanged: $wasPaid")
                }
            }
        } else {
            Timber.i(">>> Creating new transaction")
            if (transactionUI.isPaid) {
                if (transactionUI.income) {
                    amountToAdd = transactionUI.amount.amountInCents
                } else {
                    amountToDeduct = transactionUI.amount.amountInCents
                }
            } else {
                Timber.i(">>> New transaction is not paid. No balance update needed.")
                return
            }
        }

        val wallet = walletRepository.findById(transactionUI.walletId)
        if (wallet != null) {
            Timber.i(">>> Updating wallet (ID: ${wallet.uid}) balance. Deducting: $amountToDeduct, Adding: $amountToAdd")
            walletRepository.save(
                wallet.copy(
                    balance = wallet.balance - amountToDeduct + amountToAdd
                )
            )
        } else {
            Timber.e(">>> Wallet with ID ${transactionUI.walletId} not found. Cannot update balance.")
        }
    }
}