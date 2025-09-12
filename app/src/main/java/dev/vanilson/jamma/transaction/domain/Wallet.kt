package dev.vanilson.jamma.transaction.domain

data class Wallet(
    val uid: Int,
    val name: String,
    val balance: Double = 0.00,
)
