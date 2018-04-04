package com.example.t_rex.moonbookshop.adapters.book

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by t-rex on 22/03/2018.
 */
abstract class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun validateView()
    abstract fun bindAll()
}
