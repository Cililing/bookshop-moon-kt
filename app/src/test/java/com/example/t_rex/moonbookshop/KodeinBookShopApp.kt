package com.example.t_rex.moonbookshop

/**
 * Created by t-rex on 18/03/2018.
 */
class KodeinBookShopApp : MoonBookShopApp() {
    override fun isUnitTesting() : Boolean {
        return true
    }
}