package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CompoundButtonCompat
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

class LabelCheckboxElement(
        private val label: String,
        private val checked: Boolean,
        private val checkboxCallback: CheckboxCallback,
        private val groupComponent: Int = R.layout.form_group_item_inline,
        private val labelComponent: Int = R.layout.form_inline_label,
        private val checkboxComponent: Int = R.layout.form_checkbox_item,
        private val formStyleBundle: FormStyleBundle? = null
) : FormElementValid<Boolean>() {

    private var checkbox: CheckBox? = null

    override fun getVal(): Boolean? {
        return checkbox?.isChecked
    }


    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val labelView = prepareLabel(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        val checkboxView = prepareCheckbox(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(labelView)
        view.addView(checkboxView)
        return view
    }

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(labelComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        return headerView
    }

    private fun prepareCheckbox(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): CheckBox {
        val checkbox = inflater.inflate(checkboxComponent, root, false) as CheckBox


        val color = ContextCompat.getColor(context,formStyleBundle.primaryBackgroundColor)
        CompoundButtonCompat.setButtonTintList(checkbox, ColorStateList.valueOf(color))
        checkbox.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        checkbox.isChecked = checked
        checkbox.setOnClickListener {
            checkboxCallback.callback(checkbox.isChecked)
        }
        this.checkbox = checkbox
        return checkbox
    }

    public override fun enable() {
        super.disable()
        checkbox?.isEnabled = true
    }

    public override fun disable() {
        super.disable()
        checkbox?.isEnabled = false
    }
}