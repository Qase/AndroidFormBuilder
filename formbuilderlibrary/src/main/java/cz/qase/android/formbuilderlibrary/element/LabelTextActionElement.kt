package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R


class LabelTextActionElement(title: String,
                             value: String,
                             private val actionCallback: ActionCallback,
                             groupComponent: Int = R.layout.form_group_item_inline,
                             headerComponent: Int = R.layout.form_inline_label,
                             textComponent: Int = R.layout.form_inline_text,
                             formStyleBundle: FormStyleBundle? = null) :
        LabelTextElement(title, value, groupComponent, headerComponent, textComponent, formStyleBundle) {

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val view = super.createView(context, formStyleBundle)
        val animation = AlphaAnimation(1f, 0f) // Change alpha from fully visible to invisible
        animation.duration = 200 // duration - half a second
        animation.interpolator = LinearInterpolator() // do not alter animation rate
        animation.repeatMode = Animation.REVERSE; // Reverse animation at the end so the button will fade back in
        animation.repeatCount = 1
        view.setOnClickListener {
            view.startAnimation(animation)
            actionCallback.callback()
        }
        return view
    }
}