package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue

class SpaceElement(private val spaceComponent: Int = R.layout.form_space) : FormElementNoValue() {
    private var view: View? = null

    override fun hide() {
        view?.visibility = View.GONE
    }

    override fun show() {
        view?.visibility = View.VISIBLE
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewTmp = inflater.inflate(spaceComponent, null)
        viewTmp.setBackgroundColorResourceId(context, android.R.color.transparent)
        view = viewTmp
        return viewTmp
    }
}