package com.example.t_rex.moonbookshop.adapters.basket

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.t_rex.moonbookshop.R
import kotlin.reflect.KAnnotatedElement

/**
 * Created by t-rex on 24/03/2018.
 */
class BasketAdapter(private val items: List<HolderData>): RecyclerView.Adapter<BasketViewHolder>() {
    class BasketAdapterBuilder {
        private val items: MutableList<HolderData> = mutableListOf()

        fun addHolder(type: KAnnotatedElement, delegate: Any?) : BasketAdapterBuilder {
            items.add(HolderData(type, items.size, delegate))
            return this
        }

        fun build() : BasketAdapter {
            return BasketAdapter(items)
        }
    }

    data class HolderData(val type: KAnnotatedElement, val position: Int, val listener: Any?)
    private val typesAlias = mapOf(
            Pair(BasketItemViewHolder::class, 0)
    )

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BasketViewHolder {
        return when (viewType) {
            0 -> BasketItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_basket_item, parent, false))
            else -> throw IllegalStateException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: BasketViewHolder?, position: Int) {
        when (holder) {
            is BasketItemViewHolder -> holder.delegate = items[position].listener as BasketItemViewHolderDelegate
        }
        holder?.bindAll()
    }

    fun validateView(itemPosition: Int) {
        notifyItemChanged(itemPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return typesAlias[items[position].type] ?: -1
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}