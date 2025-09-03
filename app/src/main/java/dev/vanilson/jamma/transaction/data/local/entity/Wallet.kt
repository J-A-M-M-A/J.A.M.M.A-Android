package dev.vanilson.jamma.transaction.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.vanilson.jamma.transaction.domain.Wallet as WalletModel

@Entity
data class Wallet(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "balance_in_cents") val balanceInCents: Long = 0,
) {
    companion object {
        fun fromModel(walletModel: WalletModel): Wallet {
            return Wallet(
                uid = walletModel.uid,
                name = walletModel.name,
                balanceInCents = walletModel.balanceInCents,
            )
        }

        fun toModel(wallet: Wallet): WalletModel {
            return WalletModel(
                uid = wallet.uid,
                name = wallet.name,
                balanceInCents = wallet.balanceInCents,
            )
        }
    }
}
