package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid
import kotlinx.android.synthetic.main.form_header_item.view.*


class HeaderElement(val value: String, val component: Int = R.layout.form_header_item) : FormElementValid<String>() {

    override fun getVal(): String? {
        return value
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(component, null)
        view.headerValue.text = value
        view.headerValue.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        view.headerValue.setBackgroundColor(context.resources.getColor(formStyleBundle.primaryBackgroundColor))
        return view
    }
}
