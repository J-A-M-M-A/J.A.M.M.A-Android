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
        // Save the transaction
        Timber.d("Saving transaction: $transactionUI")
        transactionRepository.save(transactionUI.toTransaction())

        Timber.i(">>> Transaction saved successfully, navigating back to transaction list")

        // Update the wallet balance
        val wallet = walletRepository.findById(transactionUI.walletId)
        if (wallet != null) {
            if (transactionUI.isPaid) {
                var newBalance = wallet.balance
                if (transactionUI.income) {
                    newBalance += transactionUI.amount.amountInCents
                } else {
                    newBalance -= transactionUI.amount.amountInCents
                }
                Timber.d("Transaction is marked as paid. Updating wallet balance from ${wallet.balance} to $newBalance")
                walletRepository.save(
                    wallet.copy(
                        balance = newBalance
                    )
                )
            }
        } else {
            Timber.e("Wallet with ID ${transactionUI.walletId} not found. Cannot update balance.")
        }
    }
}