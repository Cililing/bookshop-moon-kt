package com.example.t_rex.moonbookshop.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.adapters.book.*
import com.example.t_rex.moonbookshop.basket.IBasketService
import com.example.t_rex.moonbookshop.data.BookModel
import com.example.t_rex.moonbookshop.support.getMoonActivity
import com.example.t_rex.moonbookshop.viewModels.BookViewModel
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_book.*

class BookFragment : Fragment() {
    companion object {
        fun newInstance(bookEan: String): BookFragment {
            val fragment = BookFragment()
            val args = Bundle()
            args.putString("bookEan", bookEan)
            fragment.arguments = args
            return fragment
        }
    }

    private val mBasketService: IBasketService by lazy { appKodein().instance<IBasketService>() }

    private fun getBookQuantityChooserDelegate(bookModel: BookModel): BookQuantityChooserViewHolderDelegate {
        return object : BookQuantityChooserViewHolderDelegate {
            override fun plusButtonHandler() {
                mBasketService.putBook(bookModel.ean)
                getMoonActivity().validateBasketButton()
                validate()
            }

            override fun minusButtonHandler() {
                mBasketService.decreaseBookByOne(bookModel.ean)
                getMoonActivity().validateBasketButton()
                validate()
            }

            override fun applyButtonHandler(quantity: Int) {
                mAdapter?.validateView(BookQuantityChooserViewHolder::class)
                mBasketService.setBookQuantity(bookModel.ean, quantity)
                getMoonActivity().validateBasketButton()
            }

            override fun getQuantity(): String {
                return when {
                    mBasketService.getBookQuantity(bookModel.ean) != null -> mBasketService.getBookQuantity(bookModel.ean)!!.toString()
                    else -> 0.toString()
                }
            }

            fun validate() {
                mAdapter?.validateView(BookQuantityChooserViewHolder::class)
            }
        }
    }

    private fun getPropertyDelegate(bookModel: BookModel) : BookPropertiesViewHolderDelegate {
        return object : BookPropertiesViewHolderDelegate {
            override fun getTitle(): String { return bookModel.title }

            override fun getPropertyList(): List<Pair<String, String>> {
                return mutableListOf(
                        Pair(context.getString(R.string.ean), bookModel.ean),
                        Pair(context.getString(R.string.author), bookModel.author),
                        Pair(context.getString(R.string.category), bookModel.category),
                        Pair(context.getString(R.string.rating), bookModel.rating.toString())
                ).apply {
                    if (bookModel.shortDescription != null) {
                        this.add(Pair(context.getString(R.string.description), bookModel.shortDescription!!))
                    }
                }.toList()
            }
        }
    }

    private fun getBookGalleryDelegate(bookModel: BookModel): BookGalleryViewHolderDelegate {
        return object : BookGalleryViewHolderDelegate {
            override fun getImage(): String {
                return bookModel.photoUrl ?: ""
            }
        }
    }

    private lateinit var mViewModel: BookViewModel
    private val mEan by lazy { arguments["bookEan"] as String }
    private var mAdapter: BookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
        mViewModel.init(appKodein())

        addDataObservers()
    }

    private fun addDataObservers() {
        mViewModel.getBookData(mEan).observe(this, Observer<BookModel> {
            this.mAdapter = BookAdapter.BookAdapterBuilder()
                    .addHolder(BookGalleryViewHolder::class, getBookGalleryDelegate(it!!))
                    .addHolder(BookQuantityChooserViewHolder::class, getBookQuantityChooserDelegate(it))
                    .addHolder(BookPropertiesViewHolder::class, getPropertyDelegate(it))
                    .build()
            this.rootRecyclerView.adapter = mAdapter
        })
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.rootRecyclerView.layoutManager = LinearLayoutManager(context)
        this.rootRecyclerView.layoutManager.isAutoMeasureEnabled = true
        this.rootRecyclerView.itemAnimator = DefaultItemAnimator()
    }
}
