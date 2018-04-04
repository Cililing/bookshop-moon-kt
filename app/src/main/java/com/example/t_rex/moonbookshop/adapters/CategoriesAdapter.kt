package com.example.t_rex.moonbookshop.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.t_rex.moonbookshop.R
import com.example.t_rex.moonbookshop.data.CategoryModel

/**
 * Created by t-rex on 17/03/2018.
 */
class CategoriesAdapter(private val mCategories: MutableList<CategoryModel> = mutableListOf(),
                        private val mOnClickListener: OnCategoryActionListener? = null) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    interface OnCategoryActionListener {
        fun onClick(itemView: View, categoryModel: CategoryModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int {
        return mCategories.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder?, position: Int) {
        holder?.bind(mCategories[position])
    }

    fun addItems(items: List<CategoryModel>) {
        mCategories.addAll(items)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var category: CategoryModel
        val name: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
        val icon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.icon) }

        fun bind(item: CategoryModel) {
            category = item
            name.text = item.title
            itemView.setOnClickListener {
                mOnClickListener?.onClick(itemView, category)
            }
        }
    }
}