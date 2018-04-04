package com.example.t_rex.moonbookshop.basket

import com.example.t_rex.moonbookshop.data.BookModel

/**
 * Created by t-rex on 22/03/2018.
 */
interface IBasketSharedPreferences {
    fun saveBasket(basket: Map<String, Int>)
    fun getBasket(): Map<String, Int>
}