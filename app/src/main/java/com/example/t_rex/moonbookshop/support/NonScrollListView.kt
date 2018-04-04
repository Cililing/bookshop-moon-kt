package com.example.t_rex.moonbookshop.support

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ListView

/**
 * Created by t-rex on 23/03/2018.
 */
class NonScrollListView @JvmOverloads constructor(context: Context?, attr: AttributeSet? = null, defStyle: Int = 0) : ListView(context, attr, defStyle) {
    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasureSpecCustom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpecCustom)
        val params = layoutParams
        params.height = measuredHeight
    }
}