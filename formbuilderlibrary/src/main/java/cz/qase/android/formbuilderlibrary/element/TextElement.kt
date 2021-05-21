package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue
import kotlinx.android.synthetic.main.form_text_item.view.*


class TextElement(val value: String, val component: Int = R.layout.form_text_item) : FormElementNoValue() {
    private var view: View? = null
    private var textView: TextView? = null

    override fun hide() {
        view?.visibility = View.GONE
    }

    override fun show() {
        view?.visibility = View.VISIBLE
    }
    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewTmp = inflater.inflate(component, null)
        textView = viewTmp.textValue
        viewTmp.textValue.text = value
        viewTmp.textValue.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        viewTmp.textValue.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view = viewTmp
        return viewTmp
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
    }

    fun updateText(text: String) {
        textView?.text = text
    }
}
