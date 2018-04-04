package com.example.t_rex.moonbookshop.adapters.basket

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.t_rex.moonbookshop.R
import com.squareup.picasso.Picasso

/**
 * Created by t-rex on 24/03/2018.
 */
class BasketItemViewHolder(itemView: View): BasketViewHolder(itemView) {
    private val bookImageHolder by lazy { itemView.findViewById<ImageView>(R.id.bookImage) }

    private val titleHolder by lazy { itemView.findViewById<TextView>(R.id.title) }
    private val eanHolder by lazy { itemView.findViewById<TextView>(R.id.author) }
    private val authoreHolder by lazy { itemView.findViewById<TextView>(R.id.ean) }

    private val quantityPlus by lazy { itemView.findViewById<TextView>(R.id.quantityPlus) }
    private val quaintityInput by lazy { itemView.findViewById<TextView>(R.id.quantityInput) }
    private val quantityMinus by lazy { itemView.findViewById<TextView>(R.id.quantityMinus) }

    private val unitPriceHolder by lazy { itemView.findViewById<TextView>(R.id.unitPrice) }
    private val totalPrice by lazy { itemView.findViewById<TextView>(R.id.totalPrice) }

    var delegate: BasketItemViewHolderDelegate? = null

    override fun validateView() {
        titleHolder.text = delegate?.getTitle()
        eanHolder.text = delegate?.getEan()
        authoreHolder.text = delegate?.getAuthor()
        unitPriceHolder.text = delegate?.getUnitPrice()
        totalPrice.text = delegate?.getTotalPrice()
        Picasso.get().load(delegate?.getImageUrl()).into(bookImageHolder)
    }

    override fun bindAll() {
        validateView()

        quantityPlus.setOnClickListener { delegate?.onPlusButton(adapterPosition) }
        quaintityInput.text = delegate?.getQuantity()
        quantityMinus.setOnClickListener { delegate?.onMinusButton(adapterPosition) }
    }
}