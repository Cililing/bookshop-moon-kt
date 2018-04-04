package com.example.t_rex.moonbookshop.adapters.book

import android.view.View

/**
 * Created by t-rex on 23/03/2018.
 */
interface BookQuantityChooserViewHolderDelegate {
    fun plusButtonHandler()
    fun minusButtonHandler()
    fun applyButtonHandler(quantity: Int)
    fun getQuantity(): String
}