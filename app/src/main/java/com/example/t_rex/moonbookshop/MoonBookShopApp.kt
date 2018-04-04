package com.example.t_rex.moonbookshop

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.lazy

/**
 * Created by t-rex on 15/03/2018.
 */
open class MoonBookShopApp : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        // Data Source
        import(getKodeinBaseModule.invoke(applicationContext))
        import(getKodeinOverride.invoke(applicationContext), allowOverride = true)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (!isUnitTesting()) {
                Stetho.initializeWithDefaults(this)
            }
        }
    }

    protected open fun isUnitTesting() : Boolean {
        return false
    }
}