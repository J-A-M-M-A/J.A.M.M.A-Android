package dev.vanilson.jamma.transaction.domain

data class Wallet(
    val uid: Int? = null,
    val name: String,
    val balanceInCents: Long = 0,
)
