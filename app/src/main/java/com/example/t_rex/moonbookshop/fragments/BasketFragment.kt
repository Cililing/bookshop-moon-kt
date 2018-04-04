package com.example.t_rex.moonbookshop.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.adapters.basket.BasketAdapter
import com.example.t_rex.moonbookshop.adapters.basket.BasketItemViewHolder
import com.example.t_rex.moonbookshop.adapters.basket.BasketItemViewHolderDelegate
import com.example.t_rex.moonbookshop.basket.IBasketService
import com.example.t_rex.moonbookshop.data.BookModel
import com.example.t_rex.moonbookshop.viewModels.BasketViewModel
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_basket.*


/**
 * A simple [Fragment] subclass.
 */
class BasketFragment : Fragment() {
    companion object {
        fun newInstance(): BasketFragment {
            val fragment = BasketFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun getBasketItemDelegate(basketModel: Map.Entry<BookModel, Int>): BasketItemViewHolderDelegate {
        val book = basketModel.key
        return object : BasketItemViewHolderDelegate {
            override fun getImageUrl(): String? { return book.photoUrl }
            override fun getTitle(): CharSequence? { return book.title }
            override fun getEan(): CharSequence? { return book.ean }
            override fun getAuthor(): CharSequence? { return book.author }
            override fun getQuantity(): CharSequence? {
                return when {
                    mBasketService.getBookQuantity(book.ean) != null -> mBasketService.getBookQuantity(book.ean)!!.toString()
                    else -> 0.toString()
                }
            }
            override fun onPlusButton(adapterPosition: Int) {
                mBasketService.putBook(book.ean)
                validateTotalPrice()
                mAdapter?.notifyItemChanged(adapterPosition)
            }
            override fun onMinusButton(adapterPosition: Int) {
                mBasketService.decreaseBookByOne(book.ean)
                validateTotalPrice()
                mAdapter?.notifyItemChanged(adapterPosition)
            }
            override fun getUnitPrice(): CharSequence? { return book.price.toString() }
            override fun getTotalPrice(): CharSequence? {
                val quantity = (mBasketService.getBookQuantity(book.ean) ?: 0).toDouble()
                return ((book.price ?: 0.0) * quantity).toString()
            }
        }
    }

    private val mBasketService: IBasketService by lazy { appKodein().instance<IBasketService>() }
    private lateinit var mViewModel: BasketViewModel
    private var mAdapter: BasketAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)
        mViewModel.init(appKodein())

        addDataObservers()
    }

    private fun addDataObservers() {
        mViewModel.getBasketProducts().observe(this, Observer {
            val builder = BasketAdapter.BasketAdapterBuilder()
            it?.forEach {
                builder.addHolder(BasketItemViewHolder::class, getBasketItemDelegate(it))
            }
            mAdapter = builder.build()
            this.rootRecyclerView.adapter = mAdapter
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.rootRecyclerView.layoutManager = LinearLayoutManager(context)
        this.rootRecyclerView.itemAnimator = DefaultItemAnimator()
        this.rootRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

        validateTotalPrice()
    }

    private fun validateTotalPrice() {
        this.totalPrice.text = mBasketService.calculateBasket().toString()
    }
}
