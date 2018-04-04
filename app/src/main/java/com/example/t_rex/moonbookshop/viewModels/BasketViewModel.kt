package com.example.t_rex.moonbookshop.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.t_rex.moonbookshop.basket.IBasketService
import com.example.t_rex.moonbookshop.data.BookModel
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult

/**
 * Created by t-rex on 24/03/2018.
 */
class BasketViewModel : MoonBookShopViewModel() {

    private lateinit var basketService: IBasketService
    private val allBooks by lazy { doAsyncResult { mDataBase.bookDao().getAllBooks() }.get() }

    override fun init(kodein: Kodein) {
        super.init(kodein)
        basketService = mKodein.instance()
    }

    fun getBasketProducts(): LiveData<Map<BookModel, Int>> {
        return MutableLiveData<Map<BookModel, Int>>().apply {
            doAsync { postValue(basketService.getBasket().mapKeys{ eans -> allBooks.find { book -> eans.key == book.ean }!! }) }
        }
    }
}