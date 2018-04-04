package com.example.t_rex.moonbookshop.support

import android.support.v4.app.Fragment
import com.example.t_rex.moonbookshop.activities.MainActivity

/**
 * Created by t-rex on 17/03/2018.
 */
fun numberToStringOrNull(number: Number?) : String? {
    return number?.toString()
}

fun Fragment.getMoonActivity() : MainActivity {
    return this.activity as MainActivity
}