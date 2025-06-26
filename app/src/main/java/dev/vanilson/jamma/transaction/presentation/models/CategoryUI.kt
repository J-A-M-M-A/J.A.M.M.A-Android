package dev.vanilson.jamma.transaction.presentation.models

import dev.vanilson.jamma.transaction.domain.Category

data class CategoryUI(
    val uid: Int? = null,
    val name: String,
    val icon: String,
)

fun Category.toCategoryUI(): CategoryUI {
    return CategoryUI(
        uid = uid,
        name = name,
        icon = icon
    )
}
