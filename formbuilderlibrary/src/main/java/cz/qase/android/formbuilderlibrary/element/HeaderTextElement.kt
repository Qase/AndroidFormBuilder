package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R

class HeaderTextElement(key: String,
                        protected val title: String,
                        protected val value: String,
                        protected val groupComponent: Int = R.layout.form_group_item,
                        protected val headerComponent: Int = R.layout.form_header_item,
                        protected val textComponent: Int = R.layout.form_text_item,
                        private val formStyleBundle: FormStyleBundle?) : FormElementValid<String>(key) {

    override fun getVal(): String? {
        return value
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareHeader(inflater, context, this.formStyleBundle ?: formStyleBundle)
        val textView = prepareText(inflater, context, this.formStyleBundle ?: formStyleBundle)
        view.addView(headerView)
        view.addView(textView)
        return view
    }

    private fun prepareText(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle): TextView {
        val textView = inflater.inflate(textComponent, null) as TextView
        textView.setTextColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        textView.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        textView.text = value
        return textView
    }

    private fun prepareHeader(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle): TextView {
        val headerView = inflater.inflate(headerComponent, null) as TextView
        headerView.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        headerView.setBackgroundColor(context.resources.getColor(formStyleBundle.primaryBackgroundColor))
        headerView.text = title
        return headerView
    }
}
