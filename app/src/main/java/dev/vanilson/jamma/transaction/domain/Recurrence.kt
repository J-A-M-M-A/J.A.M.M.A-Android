package dev.vanilson.jamma.transaction.domain

enum class Recurrence(val displayName: String) {
    NONE("None"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    companion object {
        fun fromDisplayName(name: String): Recurrence {
            return entries.firstOrNull { it.displayName == name } ?: NONE
        }
    }
}