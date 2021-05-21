package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue

class Divider(private val dividerComponent: Int = R.layout.form_divider) : FormElementNoValue() {

    private var view: View? = null

    override fun hide() {
        view?.visibility = View.GONE
    }

    override fun show() {
        view?.visibility = View.VISIBLE
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewTmp = inflater.inflate(dividerComponent, null)
        viewTmp.setBackgroundColorResourceId(context, formStyleBundle.dividerColor)
        view = viewTmp
        return viewTmp
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
    }
}