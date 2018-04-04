package com.example.t_rex.moonbookshop.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by t-rex on 15/03/2018.
 */
@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookModel>

    @Query("SELECT * FROM books WHERE books.category = :categoryName")
    fun getBooksFromCategory(categoryName: String) : List<BookModel>

    @Query("SELECT * FROM books WHERE has_bestseller = 1")
    fun getBestsellers() : List<BookModel>

    @Query("SELECT * FROM books WHERE ean = :ean")
    fun getBook(ean: String) : BookModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg books: BookModel)
}