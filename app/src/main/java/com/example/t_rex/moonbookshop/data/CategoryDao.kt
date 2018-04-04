package com.example.t_rex.moonbookshop.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by t-rex on 16/03/2018.
 */
@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg books: CategoryModel)
}