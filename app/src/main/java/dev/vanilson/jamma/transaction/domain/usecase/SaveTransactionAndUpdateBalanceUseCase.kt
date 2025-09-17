package dev.vanilson.jamma.transaction.domain.usecase

import dev.vanilson.jamma.transaction.domain.repository.TransactionRepository
import dev.vanilson.jamma.transaction.domain.repository.WalletRepository
import dev.vanilson.jamma.transaction.presentation.models.TransactionUI
import dev.vanilson.jamma.transaction.presentation.models.toTransaction
import timber.log.Timber

class SaveTransactionAndUpdateBalanceUseCase(
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(transactionUI: TransactionUI) {

        updateBalance(transactionUI)

        // Save the transaction
        Timber.d("Saving transaction: $transactionUI")
        transactionRepository.save(transactionUI.toTransaction())

        Timber.i(">>> Transaction saved successfully, navigating back to transaction list")

        // Update the wallet balance

    }

    fun updateBalance(transactionUI: TransactionUI) {

        var amountToDeduct = 0L
        var amountToAdd = 0L

        if (transactionUI.uid != 0) {
            Timber.i(">>> Updating existing transaction with ID: ${transactionUI.uid}")
            transactionRepository.findById(transactionUI.uid).let {
                val wasPaid = it.paidDateTime != null
                if (wasPaid && transactionUI.isPaid.not()) {
                    //need to charge the balance
                    amountToAdd = transactionUI.amount.amountInCents
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