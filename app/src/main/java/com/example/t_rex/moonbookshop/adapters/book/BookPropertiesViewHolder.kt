package com.example.t_rex.moonbookshop.adapters.book

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.t_rex.moonbookshop.R

/**
 * Created by t-rex on 23/03/2018.
 */
class BookPropertiesViewHolder(itemView: View, private val delegate: BookPropertiesViewHolderDelegate?) : BookViewHolder(itemView) {
    private val title by lazy { itemView.findViewById<TextView>(R.id.title) }
    private val propertyList by lazy { itemView.findViewById<ListView>(R.id.propertyView) }

    override fun validateView() {

    }

    override fun bindAll() {
        title.text = delegate?.getTitle()
        propertyList.adapter = PropertyAdapter(itemView.context, delegate?.getPropertyList())
    }

    private class PropertyAdapter(context: Context, properties: List<Pair<String, String>>?) : ArrayAdapter<Pair<String, String>>(context, R.layout.item_book_properties) {
        private val inflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
        private val values = properties ?: listOf()

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = inflater.inflate(R.layout.item_book_properties, parent, false)
            val propertyId = view.findViewById<TextView>(R.id.propertyId)
            val propertyValue = view.findViewById<TextView>(R.id.propertyValue)

            propertyId.text = values[position].first
            propertyValue.text = values[position].second

            return view
        }

        override fun getCount(): Int {
            return values.size
        }
    }
}