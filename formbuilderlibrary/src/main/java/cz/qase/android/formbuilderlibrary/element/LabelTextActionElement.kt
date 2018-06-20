package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.MotionEvent
import android.view.View
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
        view.setOnClickListener({
            actionCallback.callback()
        })
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    view.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
                }
                MotionEvent.ACTION_DOWN -> {
                    view.setBackgroundColor(context.resources.getColor(formStyleBundle.primaryBackgroundColor))
                }
            }
            true
        }
        return view
    }
}