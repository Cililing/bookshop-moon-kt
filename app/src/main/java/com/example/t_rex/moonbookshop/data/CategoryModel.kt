package com.example.t_rex.moonbookshop.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by t-rex on 16/03/2018.
 */
@Entity(tableName = "categories")
data class CategoryModel(
        @ColumnInfo(name = "name") @PrimaryKey var title: String
) {
    companion object {
        const val defaultCategoryName = "Unknown"
    }
}