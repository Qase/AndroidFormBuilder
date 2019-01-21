package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue

class NavigationElement(private val actionCallback: ActionCallback,
                        private val label: String,
                        private val groupComponent: Int = R.layout.form_group_item_inline,
                        private val headerComponent: Int = R.layout.form_inline_label,
                        private val symbolComponent: Int = R.layout.form_navigation_symbol,
                        private val formStyleBundle: FormStyleBundle? = null) : FormElementNoValue() {

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareLabel(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val symbolView = prepareSymbol(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        view.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        view.addView(headerView)
        view.addView(symbolView)

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

    private fun prepareSymbol(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): ImageView {
        val symbolView = inflater.inflate(symbolComponent, root, false) as ImageView
        symbolView.setColorFilter(context.resources.getColor(formStyleBundle.secondaryTextColor))
        return symbolView
    }

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        headerView.text = label
        return headerView
    }
}