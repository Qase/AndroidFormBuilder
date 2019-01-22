package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid

class LabelSwitchElement(private val title: String,
                         private var on: Boolean,
                         private val checkboxCallback: CheckboxCallback,
                         private val groupComponent: Int = R.layout.form_group_item_inline,
                         private val headerComponent: Int = R.layout.form_inline_label,
                         private val switchComponent: Int = R.layout.form_inline_switch,
                         private val formStyleBundle: FormStyleBundle? = null) : FormElementValid<Boolean>() {
    override fun getVal(): Boolean {
        return on
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareHeader(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val textView = prepareSwitch(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(headerView)
        view.addView(textView)
        return view
    }

    private fun prepareSwitch(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): Switch {
        val switchView = inflater.inflate(switchComponent, root, false) as Switch
        switchView.isChecked = on
        switchView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        switchView.setOnCheckedChangeListener { _, isChecked ->
            on = isChecked
            checkboxCallback.callback(on)
        }
        return switchView
    }

    private fun prepareHeader(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = title
        return headerView
    }
}
