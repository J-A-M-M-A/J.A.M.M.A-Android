package dev.vanilson.jamma.transaction.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.vanilson.jamma.transaction.domain.Wallet as WalletModel

@Entity
data class Wallet(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "balance") val balance: Long = 0L,
) {
    companion object {
        fun fromModel(walletModel: WalletModel): Wallet {
            return Wallet(
                uid = walletModel.uid,
                name = walletModel.name,
                balance = walletModel.balance,
            )
        }

        fun toModel(wallet: Wallet): WalletModel {
            return WalletModel(
                uid = wallet.uid,
                name = wallet.name,
                balance = wallet.balance,
            )
        }
    }
}
