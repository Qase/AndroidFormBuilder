package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R

class HeaderTextViewElement(key: String,
                            val title: String,
                            val value: String,
                            val groupComponent : Int = R.layout.form_group_item,
                            val headerComponent: Int = R.layout.form_header_item,
                            val textComponent: Int = R.layout.form_text_item) : FormElementValid<String>(key) {

    override fun getVal(): String? {
        return value
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = inflater.inflate(headerComponent, null) as TextView
        val textView = inflater.inflate(textComponent, null) as TextView
        headerView.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        headerView.setBackgroundColor(context.resources.getColor(formStyleBundle.primaryBackgroundColor))
        headerView.text = title
        textView.setTextColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        textView.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        textView.text = title
        view.addView(headerView)
        view.addView(textView)
        return view
    }
}
