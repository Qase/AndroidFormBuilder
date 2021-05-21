package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback


class ActionTextElement(
    private val actionCallback: ActionCallback,
    label: String,
    text: String = "",
    groupComponent: Int = R.layout.form_group_item_inline,
    headerComponent: Int = R.layout.form_inline_label,
    textComponent: Int = R.layout.form_inline_text,
    formStyleBundle: FormStyleBundle? = null
) :
    LabelTextElement(label, text, groupComponent, headerComponent, textComponent, formStyleBundle) {
    private var viewGroup: View? = null

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val view = super.createView(context, formStyleBundle)
        enableClick(view, context, formStyleBundle)
        viewGroup = view
        return view
    }

    private fun enableClick(
        view: View,
        context: Context,
        formStyleBundle: FormStyleBundle
    ) {
        view.isClickable = true
        view.setOnClickListener {
            actionCallback.callback()
        }
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                }
                MotionEvent.ACTION_DOWN -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.primaryBackgroundColor
                    )
                }
            }
            true
        }
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        viewGroup?.let {
            enableClick(it, context, formStyleBundle)
        }
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).disabledBackgroundColor
        )
        viewGroup?.isClickable = false
        viewGroup?.setOnTouchListener(null)
        viewGroup?.setOnClickListener(null)
    }
}