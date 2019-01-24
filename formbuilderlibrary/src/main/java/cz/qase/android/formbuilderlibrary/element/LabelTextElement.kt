package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue

open class LabelTextElement(private val label: String,
                            private val text: String,
                            private val groupComponent: Int = R.layout.form_group_item_inline,
                            private val headerComponent: Int = R.layout.form_inline_label,
                            private val textComponent: Int = R.layout.form_inline_text,
                            private val formStyleBundle: FormStyleBundle? = null) : FormElementNoValue() {

    private var textView: TextView? = null
    private var labelView: TextView? = null
    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareLabel(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val textView = prepareText(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(headerView)
        view.addView(textView)
        return view
    }

    private fun prepareText(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        textView.text = text
        this.textView = textView
        return textView
    }

    fun updateLabel(label: String) {
        textView?.text = label
    }

    fun updateText(text: String) {
        textView?.text = text
    }

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        labelView = headerView
        return headerView
    }
}