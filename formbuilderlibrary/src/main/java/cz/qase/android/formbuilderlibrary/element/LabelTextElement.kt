package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R

class LabelTextElement(key: String,
                       private val title: String,
                       private val value: String,
                       private val groupComponent: Int = R.layout.form_group_item_inline,
                       private val headerComponent: Int = R.layout.form_inline_label,
                       private val textComponent: Int = R.layout.form_inline_text,
                       private val formStyleBundle: FormStyleBundle? = null) : FormElementValid<String>(key) {
    override fun getVal(): String? {
        return value
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareHeader(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val textView = prepareText(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        view.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        view.addView(headerView)
        view.addView(textView)
        return view
    }

    private fun prepareText(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        textView.text = value
        return textView
    }

    private fun prepareHeader(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        headerView.text = title
        return headerView
    }
}