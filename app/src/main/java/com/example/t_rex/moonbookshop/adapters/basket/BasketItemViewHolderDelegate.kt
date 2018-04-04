package com.example.t_rex.moonbookshop.adapters.basket

/**
 * Created by t-rex on 24/03/2018.
 */
interface BasketItemViewHolderDelegate {
    fun getTitle(): CharSequence?
    fun getEan(): CharSequence?
    fun getAuthor(): CharSequence?
    fun getImageUrl(): String?

    fun getQuantity(): CharSequence?
    fun onPlusButton(adapterPosition: Int)
    fun onMinusButton(adapterPosition: Int)

    fun getUnitPrice(): CharSequence?
    fun getTotalPrice(): CharSequence?
}