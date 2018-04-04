package com.example.t_rex.moonbookshop.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.data.BookModel
import com.example.t_rex.moonbookshop.support.numberToStringOrNull
import com.squareup.picasso.Picasso

/**
 * Created by t-rex on 17/03/2018.
 */
interface BooksAdapterHandler {
    fun onBookClicked(book: BookModel, adapterPosition: Int)
}

class BooksAdapter(private val mBooks: MutableList<BookModel> = mutableListOf(),
                   private val mListener: BooksAdapterHandler) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_book, null, false))
    }

    override fun getItemCount(): Int {
        return mBooks.size
    }

    override fun onBindViewHolder(holder: BookViewHolder?, position: Int) {
        holder?.bind(mBooks[position])
    }

    fun addItems(books: List<BookModel>) {
        this.mBooks.addAll(books)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumb: ImageView by lazy { itemView.findViewById<ImageView>(R.id.thumb) }
        val title: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
        val price: TextView by lazy { itemView.findViewById<TextView>(R.id.price) }

        fun bind(item: BookModel) {
            this.title.text = item.title
            this.price.text = numberToStringOrNull(item.price) ?: itemView.context.getString(R.string.no_price)
            Picasso.get().load(item.photoUrl).into(thumb)

            itemView.setOnClickListener { mListener.onBookClicked(mBooks[adapterPosition], adapterPosition) }
        }
    }
}

