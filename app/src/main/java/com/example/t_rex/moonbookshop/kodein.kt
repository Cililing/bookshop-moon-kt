package com.example.t_rex.moonbookshop

import android.arch.persistence.room.Room
import android.content.Context
import com.example.t_rex.moonbookshop.basket.BasketService
import com.example.t_rex.moonbookshop.basket.BasketSharedPreferences
import com.example.t_rex.moonbookshop.basket.IBasketService
import com.example.t_rex.moonbookshop.basket.IBasketSharedPreferences
import com.example.t_rex.moonbookshop.data.AppDatabase
import com.github.salomonbrys.kodein.*
import org.jetbrains.anko.doAsyncResult

/**
 * Put your app dependencies here
 */
val getKodeinBaseModule = fun(applicationContext: Context): Kodein.Module {
    return Kodein.Module(allowSilentOverride = false) {
        bind<AppDatabase>() with singleton { Room.databaseBuilder(applicationContext, AppDatabase::class.java, "moonDb")
                .fallbackToDestructiveMigration()
                .build() }

        bind<IBasketSharedPreferences>() with singleton { BasketSharedPreferences(applicationContext) }
        bind<IBasketService>() with singleton {
            doAsyncResult {
                BasketService(
                        instance(), // IBasketSharedPreferences
                        instance()) // AppDatabase
            }.get()
        }
    }
}

/**
 * This property should be overwritten in test cases.
 */
var getKodeinOverride = fun(_: Context): Kodein.Module {
    return Kodein.Module(allowSilentOverride = true) {
    }
}

