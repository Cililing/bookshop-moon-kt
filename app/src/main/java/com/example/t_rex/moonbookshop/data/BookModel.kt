package com.example.t_rex.moonbookshop.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by t-rex on 15/03/2018.
 */
@Entity(tableName = "books",
        foreignKeys = [(ForeignKey(
                entity = CategoryModel::class,
                parentColumns = arrayOf("name"),
                childColumns = arrayOf("category"),
                onDelete = ForeignKey.CASCADE))])
data class BookModel(
        @ColumnInfo(name = "ean") @PrimaryKey var ean: String,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "author") var author: String = "Unknown",
        @ColumnInfo(name = "price") var price: Double? = null,
        @ColumnInfo(name = "rating") var rating: Double? = null,
        @ColumnInfo(name = "short_description") var shortDescription: String? = null,

        @ColumnInfo(name = "description") var description: String? = null,
        @ColumnInfo(name = "photoUrl") var photoUrl: String? = null,
        @ColumnInfo(name = "has_bestseller") var isBestseller: Boolean = false,
        @ColumnInfo(name = "category") var category: String = CategoryModel.defaultCategoryName
)

