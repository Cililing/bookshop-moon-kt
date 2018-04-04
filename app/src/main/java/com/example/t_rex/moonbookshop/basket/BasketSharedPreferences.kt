package com.example.t_rex.moonbookshop.basket

import android.content.Context
import com.example.t_rex.moonbookshop.data.BookModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by t-rex on 17/03/2018.
 */
class BasketSharedPreferences(private val mApplicationContext: Context) : IBasketSharedPreferences {
    companion object {
        private const val sharePrefName = "MoonBasket"
        private const val basketPrefName = "Basket"
        private var basketType = object : TypeToken<Map<String, Int>>(){}.type!!
    }

    @Synchronized override fun saveBasket(basket: Map<String, Int>) {
        val json = Gson().toJson(basket)
        mApplicationContext.getSharedPreferences(sharePrefName, Context.MODE_PRIVATE).edit().apply {
            putString(basketPrefName, json)
        }.apply()
    }

    @Synchronized override fun getBasket(): Map<String, Int> {
        val basket = mApplicationContext.getSharedPreferences(sharePrefName, Context.MODE_PRIVATE).getString(basketPrefName, "[]")
        return Gson().fromJson(basket, basketType)
    }
}