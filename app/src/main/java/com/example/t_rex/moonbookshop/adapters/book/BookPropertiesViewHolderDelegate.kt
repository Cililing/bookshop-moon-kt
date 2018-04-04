package com.example.t_rex.moonbookshop.adapters.book

/**
 * Created by t-rex on 23/03/2018.
 */
interface BookPropertiesViewHolderDelegate {
    fun getTitle(): String
    fun getPropertyList(): List<Pair<String, String>>
}