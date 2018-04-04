package com.example.t_rex.moonbookshop.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.t_rex.moonbookshop.data.CategoryModel
import org.jetbrains.anko.doAsync

/**
 * Created by t-rex on 17/03/2018.
 */
class CategoriesViewModel : MoonBookShopViewModel() {

    fun getCategories() : LiveData<List<CategoryModel>> {
        val data = MutableLiveData<List<CategoryModel>>()
        doAsync {
            data.postValue(mDataBase.categoryDao().getAllCategories())
        }
        return data
    }
}