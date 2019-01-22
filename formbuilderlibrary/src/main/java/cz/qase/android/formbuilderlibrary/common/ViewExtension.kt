package cz.qase.android.formbuilderlibrary.common

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

fun View.setBackgroundColorResourceId(ctx: Context, @ColorRes id: Int){
    val color = ContextCompat.getColor(ctx,id)
    this.setBackgroundColor(color)
}

fun TextView.setTextColorResourceId(ctx: Context, @ColorRes id: Int){
    val color = ContextCompat.getColor(ctx,id)
    this.setTextColor(color)
}
