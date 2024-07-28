package dev.vanilson.jamma.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.vanilson.jamma.domain.model.Category as CategoryModel

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "icon") val icon: String,
) {
    companion object {
        fun fromModel(category: CategoryModel): Category {
            return Category(
                uid = category.uid,
                name = category.name,
                icon = category.icon
            )
        }

        fun toModel(category: Category): CategoryModel {
            return CategoryModel(
                uid = category.uid,
                name = category.name,
                icon = category.icon
            )
        }
    }
}