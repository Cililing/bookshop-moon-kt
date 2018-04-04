package com.example.t_rex.moonbookshop.adapters.book

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.t_rex.moonbookshop.R
import kotlin.reflect.KAnnotatedElement

/**
 * Created by t-rex on 22/03/2018.
 */
class BookAdapter private constructor(private val holders: List<KAnnotatedElement>,
                                      private val delegates: Map<KAnnotatedElement, Any?>) : RecyclerView.Adapter<BookViewHolder>() {

    class BookAdapterBuilder {
        private val holders: MutableSet<KAnnotatedElement> = mutableSetOf()
        private val delegates: MutableMap<KAnnotatedElement, Any?> = mutableMapOf()

        fun addHolder(type: KAnnotatedElement, delegate: Any?) : BookAdapterBuilder {
            holders.add(type)
            delegates[type] = delegate
            return this
        }

        fun build() : BookAdapter {
            return BookAdapter(holders.toList(), delegates)
        }
    }

    // Property, Code
    // Have to be unique!!!
    private val holderMap: Map<KAnnotatedElement, Int> = mapOf(
            Pair(BookGalleryViewHolder::class, 0),
            Pair(BookQuantityChooserViewHolder::class, 1),
            Pair(BookPropertiesViewHolder::class, 2)
    )

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookViewHolder {
        return when (viewType) {
            0 -> BookGalleryViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_gallery, parent, false),
                    delegates[BookGalleryViewHolder::class] as? BookGalleryViewHolderDelegate)
            1 -> BookQuantityChooserViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_quantity_book, parent, false),
                    delegates[BookQuantityChooserViewHolder::class] as? BookQuantityChooserViewHolderDelegate)
            2 -> BookPropertiesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_properties_book, parent, false),
                    delegates[BookPropertiesViewHolder::class] as? BookPropertiesViewHolderDelegate)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder?, position: Int) {
        holder?.bindAll()
    }

    fun validateView(type: KAnnotatedElement) {
        notifyItemChanged(holders.indexOf(type))
    }

    override fun getItemViewType(position: Int): Int {
        return holderMap[holders[position]] ?: -1
    }

    override fun getItemCount(): Int {
        return holders.count()
    }

}