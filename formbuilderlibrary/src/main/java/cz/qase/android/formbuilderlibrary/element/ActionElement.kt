package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.MotionEvent
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback


class ActionElement(private val actionCallback: ActionCallback,
                    label: String,
                    text: String = "",
                    groupComponent: Int = R.layout.form_group_item_inline,
                    headerComponent: Int = R.layout.form_inline_label,
                    textComponent: Int = R.layout.form_inline_text,
                    formStyleBundle: FormStyleBundle? = null) :
        LabelTextElement(label, text, groupComponent, headerComponent, textComponent, formStyleBundle) {

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val view = super.createView(context, formStyleBundle)
        view.isClickable = true
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