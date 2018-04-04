package com.example.t_rex.moonbookshop.basket

import com.example.t_rex.moonbookshop.data.AppDatabase
import com.example.t_rex.moonbookshop.data.BookModel
import org.jetbrains.anko.doAsync

/**
 * Created by t-rex on 17/03/2018.
 */
class BasketService(private val basketSharedPreferences: IBasketSharedPreferences, private val mDatabase: AppDatabase) : IBasketService {
    private lateinit var mBasket: MutableMap<String, Int>
    private lateinit var mBookList: List<BookModel>
    init {
        synchronized(this) {
            mBasket = basketSharedPreferences.getBasket().toMutableMap()
            mBookList = mDatabase.bookDao().getAllBooks()
        }
    }

    override fun putBook(ean: String): Boolean {
        mBasket[ean] = (mBasket[ean] ?: 0) + 1
        saveBasket()
        return true
    }

    override fun decreaseBookByOne(ean: String) : Boolean {
        val success = when {
            mBasket[ean] == null -> false
            mBasket[ean]!! <= 0 -> false
            else -> {
                mBasket[ean] = mBasket[ean]!! - 1
                true
            }
        }
        saveBasket()
        return success
    }

    override fun setBookQuantity(ean: String, quantity: Int) : Boolean {
        if (quantity < 0) return false

        when (quantity) {
            0 -> mBasket.remove(ean)
            else -> mBasket[ean] = quantity
        }

        saveBasket()
        return true
    }

    override fun getBookQuantity(ean: String): Int? {
        return mBasket[ean]
    }

    override fun getBasket(): Map<String, Int> {
        return mBasket.toMap()
    }

    override fun clearBasket() {
        mBasket.clear()
        saveBasket()
    }

    override fun contains(ean: String): Int? {
        return mBasket[ean] ?: 0
    }

    private fun saveBasket() {
        synchronized(this, {
            basketSharedPreferences.saveBasket(mBasket)
        })
    }

    override fun calculateBasket() : Double {
        val priceMap = mBasket.map { item ->
            val bookPrice = mBookList.find { book -> book.ean == item.key }?.price
            item.value.toDouble() * (bookPrice ?: 0.0)
        }

        return priceMap.fold(0.0, { acc, d -> acc + d })
    }
}