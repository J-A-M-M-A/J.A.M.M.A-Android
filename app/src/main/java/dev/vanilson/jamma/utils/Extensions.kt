package dev.vanilson.jamma.utils

import java.text.DecimalFormat
import java.util.Locale

data class FormatedMoney(
    val amountInCents: Long,
    val formatted: String
)

fun Long.toFormattedMoney(): FormatedMoney {
    val formatted = String.format(Locale.getDefault(), "%.2f", this / 100.0)
    return FormatedMoney(this, formatted)
}

fun String.toFormattedMoney(): FormatedMoney {
    val amountInCents = (this.replace(".", "").replace(",", "").toLong())
    return amountInCents.toFormattedMoney()
}

fun Double.toMoney(): String {
    val format = DecimalFormat("#,##0.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}