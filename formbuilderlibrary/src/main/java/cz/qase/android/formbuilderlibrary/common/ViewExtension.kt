package cz.qase.android.formbuilderlibrary.common

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.setBackgroundColorResourceId(ctx: Context, @ColorRes id: Int){
    val color = ContextCompat.getColor(ctx,id)
    this.setBackgroundColor(color)
}

fun TextView.setTextColorResourceId(ctx: Context, @ColorRes id: Int){
    val color = ContextCompat.getColor(ctx,id)
    this.setTextColor(color)
}
