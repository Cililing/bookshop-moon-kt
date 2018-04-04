package com.example.t_rex.moonbookshop.adapters.book

import android.view.View
import android.widget.ImageView
import com.example.t_rex.moonbookshop.R
import com.squareup.picasso.Picasso

/**
 * Created by t-rex on 22/03/2018.
 */
class BookGalleryViewHolder(itemView: View, private val delegate: BookGalleryViewHolderDelegate?) : BookViewHolder(itemView) {
    private val photo by lazy { itemView.findViewById<ImageView>(R.id.bookImage) }

    override fun bindAll() {
        Picasso.get().load(delegate?.getImage()).into(photo)
    }

    override fun validateView() {
        Picasso.get().load(delegate?.getImage()).into(photo)
    }
}