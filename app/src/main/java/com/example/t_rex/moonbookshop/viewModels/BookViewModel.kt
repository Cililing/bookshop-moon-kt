package com.example.t_rex.moonbookshop.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.t_rex.moonbookshop.data.BookModel
import org.jetbrains.anko.doAsync

/**
 * Created by t-rex on 22/03/2018.
 */
class BookViewModel : MoonBookShopViewModel() {

    fun getBookData(ean: String) : LiveData<BookModel> {
        val data = MutableLiveData<BookModel>()
        doAsync { data.postValue(mDataBase.bookDao().getBook(ean)) }
        return data
    }
}