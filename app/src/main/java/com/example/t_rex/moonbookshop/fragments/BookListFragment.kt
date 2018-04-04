package com.example.t_rex.moonbookshop.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.adapters.BooksAdapter
import com.example.t_rex.moonbookshop.adapters.BooksAdapterHandler
import com.example.t_rex.moonbookshop.data.BookModel
import com.example.t_rex.moonbookshop.support.getMoonActivity
import com.example.t_rex.moonbookshop.viewModels.BookListViewModel
import com.github.salomonbrys.kodein.android.appKodein
import kotlinx.android.synthetic.main.fragment_book_list.*
import java.io.Serializable

class BookListFragment : Fragment() {
    companion object {
        enum class Type(val id: Int) { BESTSELLERS(0), CATEGORY(1), CUSTOM(2) }

        data class BookListViewParams(val type: Type,
                                      val category: String? = null,
                                      val title: String? = null) : Serializable

        fun newInstance(params: BookListViewParams): BookListFragment {
            val fragment = BookListFragment()
            val args = Bundle()
            args.putSerializable("bundle", params)
            fragment.arguments = args
            return fragment
        }
    }

    private val mParams by lazy { arguments["bundle"] as BookListViewParams}
    private lateinit var mViewModel: BookListViewModel
    private val mAdapter = BooksAdapter(
            mListener = object : BooksAdapterHandler {
                override fun onBookClicked(book: BookModel, adapterPosition: Int) {
                    getMoonActivity().changeFragment(BookFragment.newInstance(book.ean))
                }
            }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)
        mViewModel.init(appKodein())

        addDataObservers()
    }

    private fun addDataObservers() {
        if (mParams.type == Type.CATEGORY) {
            mViewModel.getBooksByCategory(mParams.category!!).observe(this, Observer<List<BookModel>> {
                mAdapter.addItems(it!!)
            })
        }
        else if (mParams.type == Type.BESTSELLERS) {
            mViewModel.getBestsellers().observe(this, Observer<List<BookModel>> {
                mAdapter.addItems(it!!)
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.booksList.layoutManager = GridLayoutManager(context, 2)
        this.booksList.itemAnimator = DefaultItemAnimator()
        this.booksList.adapter = mAdapter
    }
}
