package com.example.t_rex.moonbookshop.viewModels

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import com.example.t_rex.moonbookshop.data.AppDatabase
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance

/**
 * Created by t-rex on 17/03/2018.
 */
open class MoonBookShopViewModel : ViewModel() {

    protected lateinit var mKodein: Kodein
    protected lateinit var mDataBase: AppDatabase

    @CallSuper
    open fun init(kodein: Kodein) {
        this.mKodein = kodein
        this.mDataBase = kodein.instance()
    }

}