package dev.vanilson.jamma.transaction.domain.usecase

import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.domain.repository.WalletRepository
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toTransaction
import timber.log.Timber

class DeleteTransactionAndUpdateBalanceUseCase(
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(transactionUI: TransactionUI) {
        updateBalance(transactionUI)
        Timber.d("Saving transaction: $transactionUI")
        transactionRepository.delete(transactionUI.toTransaction())
    }

    fun updateBalance(transactionUI: TransactionUI) {
        var amountToAdd = 0L
        var amountToDeduct = 0L
        transactionRepository.findById(transactionUI.uid).let {
            val wasPaid = it.paidDateTime != null
            if (wasPaid) {
                //need to charge the balance
                if (transactionUI.income) {
                    amountToDeduct = transactionUI.amount.amountInCents
                } else {
                    amountToAdd = transactionUI.amount.amountInCents
                }
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
            Timber.e("Wallet with ID ${transactionUI.walletId} not found. Cannot update balance.")
        }
    }
}