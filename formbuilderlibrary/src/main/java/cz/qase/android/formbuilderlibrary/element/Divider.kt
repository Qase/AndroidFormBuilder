package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R

class Divider(private val dividerComponent: Int = R.layout.form_divider) : FormElementValid<Unit>() {

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(dividerComponent, null)
        view.setBackgroundColor(context.resources.getColor(formStyleBundle.dividerColor))
        return view
    }

    override fun getVal() {

    }
}