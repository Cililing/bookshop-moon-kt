package com.example.t_rex.moonbookshop.basket

/**
 * Created by t-rex on 22/03/2018.
 */
interface IBasketService {
    fun putBook(ean: String): Boolean
    fun decreaseBookByOne(ean: String) : Boolean
    fun setBookQuantity(ean: String, quantity: Int) : Boolean
    fun getBookQuantity(ean: String): Int?
    fun getBasket(): Map<String, Int>
    fun clearBasket()
    fun contains(book: String): Int?
    fun calculateBasket() : Double
}