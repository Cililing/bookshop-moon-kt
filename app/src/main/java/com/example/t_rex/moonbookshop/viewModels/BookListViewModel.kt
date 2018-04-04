package com.example.t_rex.moonbookshop.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.t_rex.moonbookshop.data.BookModel
import org.jetbrains.anko.doAsync

/**
 * Created by t-rex on 17/03/2018.
 */
class BookListViewModel : MoonBookShopViewModel() {

    private fun getBooks(query: () -> List<BookModel>) : LiveData<List<BookModel>> {
        return MutableLiveData<List<BookModel>>().apply {
            doAsync { postValue(query.invoke()) }
        }
    }

    fun getBooksByCategory(category: String) : LiveData<List<BookModel>> {
        return getBooks { mDataBase.bookDao().getBooksFromCategory(category) }
    }

    fun getBestsellers() : LiveData<List<BookModel>> {
        return getBooks { mDataBase.bookDao().getBestsellers() }
    }
}