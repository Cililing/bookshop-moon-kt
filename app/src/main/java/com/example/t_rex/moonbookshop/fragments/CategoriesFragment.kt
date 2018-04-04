package com.example.t_rex.moonbookshop.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.activities.MainActivity
import com.example.t_rex.moonbookshop.adapters.CategoriesAdapter
import com.example.t_rex.moonbookshop.data.CategoryModel
import com.example.t_rex.moonbookshop.viewModels.CategoriesViewModel
import com.github.salomonbrys.kodein.android.appKodein
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {
    companion object {
        fun newInstance(): CategoriesFragment {
            val fragment = CategoriesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mViewModel: CategoriesViewModel

    private val mListener = object : CategoriesAdapter.OnCategoryActionListener {
        override fun onClick(itemView: View, categoryModel: CategoryModel) {
            val params = BookListFragment.Companion.BookListViewParams(
                    type = BookListFragment.Companion.Type.CATEGORY,
                    category = categoryModel.title
            )

            (activity as MainActivity).changeFragment(BookListFragment.newInstance(params))
        }
    }

    private val mAdapter: CategoriesAdapter = CategoriesAdapter(
            mOnClickListener = mListener
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        mViewModel.init(appKodein())

        addDataObservers()
    }

    private fun addDataObservers() {
        mViewModel.getCategories().observe(this, Observer<List<CategoryModel>> {
            mAdapter.addItems(it!!)
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        this.categoriesList.layoutManager = layoutManager
        this.categoriesList.itemAnimator = DefaultItemAnimator()
        this.categoriesList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        this.categoriesList.adapter = mAdapter
    }
}