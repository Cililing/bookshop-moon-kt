package com.example.t_rex.moonbookshop.adapters.book

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.t_rex.moonbookshop.R

/**
 * Created by t-rex on 22/03/2018.
 * [R.layout.view_holder_quantity_book]
 */

class BookQuantityChooserViewHolder(itemView: View, private val delegate: BookQuantityChooserViewHolderDelegate?) : BookViewHolder(itemView) {
    private val plusButton by lazy { itemView.findViewById<Button>(R.id.plusButton) }
    private val minusButton by lazy { itemView.findViewById<Button>(R.id.minusButton) }
    private val input by lazy { itemView.findViewById<EditText>(R.id.quantityInput) }
    private val applyButton by lazy { itemView.findViewById<Button>(R.id.applyButton) }

    private fun bindDelegate() {
        plusButton.setOnClickListener { delegate?.plusButtonHandler() }
        minusButton.setOnClickListener { delegate?.minusButtonHandler() }
        applyButton.setOnClickListener { delegate?.applyButtonHandler(Integer.parseInt(input.text.toString())) }
    }

    private fun bindView() {
        input.setText(delegate?.getQuantity())
    }

    override fun validateView() {
        input.setText(delegate?.getQuantity())
    }

    override fun bindAll() {
        bindDelegate()
        bindView()
    }
}