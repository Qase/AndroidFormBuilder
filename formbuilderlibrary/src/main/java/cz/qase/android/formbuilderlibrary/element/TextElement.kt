package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import kotlinx.android.synthetic.main.form_text_item.view.*


class TextElement(val value: String, val component: Int = R.layout.form_text_item) : FormElementValid<String>() {

    override fun getVal(): String? {
        return value
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(component, null)
        view.textValue.text = value
        view.textValue.setTextColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        view.textValue.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        return view
    }
}
