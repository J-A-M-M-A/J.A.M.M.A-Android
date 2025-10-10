package dev.vanilson.jamma.transaction.domain

sealed class Recurrence {
    object None : Recurrence()
    object Daily : Recurrence()
    object Weekly : Recurrence()
    object Monthly : Recurrence()
    object Yearly : Recurrence()
}