package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid

class HeadCheckboxElement(
        private val checked: Boolean,
        val title: String,
        val hint: String? = null,
        val checkboxCallback: CheckboxCallback,
        protected val groupComponent: Int = R.layout.form_group_item,
        protected val headerComponent: Int = R.layout.form_header_item,
        protected val checkboxComponent: Int = R.layout.form_checkbox_item
) : FormElementValid<Boolean>() {

    private lateinit var checkbox: CheckBox

    override fun getVal(): Boolean? {
        return checkbox.isChecked
    }


    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareHeader(inflater, context, formStyleBundle)
        val checkboxView = prepareCheckbox(inflater, context, formStyleBundle)
        view.addView(headerView)
        view.addView(checkboxView)
        return view
    }

    private fun prepareCheckbox(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle): CheckBox {
        checkbox = inflater.inflate(checkboxComponent, null) as CheckBox
        checkbox.setTextColor(ContextCompat.getColor(context, formStyleBundle.secondaryTextColor))
        checkbox.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        checkbox.text = hint
        checkbox.isChecked = checked
        checkbox.setOnClickListener {
            checkboxCallback.callback(checkbox.isChecked)
        }
        return checkbox
    }

    private fun prepareHeader(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle): TextView {
        val headerView = inflater.inflate(headerComponent, null) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.setBackgroundColorResourceId(context, formStyleBundle.primaryBackgroundColor)
        headerView.text = title
        return headerView
    }
}
