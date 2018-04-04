package com.example.t_rex.moonbookshop.adapters.basket

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by t-rex on 24/03/2018.
 */
abstract class BasketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun validateView()
    abstract fun bindAll()
}