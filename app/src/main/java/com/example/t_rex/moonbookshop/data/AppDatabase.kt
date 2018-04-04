package com.example.t_rex.moonbookshop.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by t-rex on 15/03/2018.
 */
@Database(entities = [(BookModel::class), (CategoryModel::class)], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun categoryDao(): CategoryDao
}