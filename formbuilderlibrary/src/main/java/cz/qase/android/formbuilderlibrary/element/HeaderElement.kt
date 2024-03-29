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


class HeaderElement(
    val value: String,
    val component: Int = R.layout.form_header_item,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementNoValue() {

    private var view: View? = null

    override fun hide() {
        view?.visibility = View.GONE
    }

    override fun show() {
        view?.visibility = View.VISIBLE
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val usedFormStyleBundle = this.formStyleBundle ?: formStyleBundle
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewTmp = inflater.inflate(component, null) as TextView
        viewTmp.text = value
        viewTmp.textSize
        viewTmp.setTextColorResourceId(context, usedFormStyleBundle.primaryTextColor)
        viewTmp.setBackgroundColorResourceId(
            context,
            usedFormStyleBundle.primaryBackgroundColor
        )
        view = viewTmp
        return viewTmp
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
    }
}
