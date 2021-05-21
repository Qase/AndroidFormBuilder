package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.CompoundButtonCompat
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid

class LabelCheckboxElement(
    private val label: String,
    private val checked: Boolean,
    private val checkboxCallback: CheckboxCallback? = null,
    private val groupComponent: Int = R.layout.form_group_item_inline,
    private val labelComponent: Int = R.layout.form_inline_label,
    private val checkboxComponent: Int = R.layout.form_checkbox_item,
    private val groupComponentEnd: Int = R.layout.form_group_item_inline_end,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementValid<Boolean>() {

    private var checkbox: CheckBox? = null
    private var viewGroup: ViewGroup? = null

    override fun hide() {
        viewGroup?.visibility = View.GONE
    }

    override fun show() {
        viewGroup?.visibility = View.VISIBLE
    }

    override fun getVal(): Boolean? {
        return checkbox?.isChecked
    }


    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val groupEnd = inflater.inflate(groupComponentEnd, view, false) as ViewGroup
        val labelView = prepareLabel(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        val checkboxView = prepareCheckbox(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, groupEnd
        )
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(labelView)
        groupEnd.addView(checkboxView)
        view.addView(groupEnd)
        viewGroup = view
        return view
    }

    private fun prepareLabel(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): TextView {
        val headerView = inflater.inflate(labelComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        return headerView
    }

    private fun prepareCheckbox(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): CheckBox {

        val checkbox = inflater.inflate(checkboxComponent, root, false) as CheckBox
        val color = ContextCompat.getColor(context, formStyleBundle.primaryBackgroundColor)
        CompoundButtonCompat.setButtonTintList(checkbox, ColorStateList.valueOf(color))
        checkbox.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        checkbox.isChecked = checked
        checkbox.setOnClickListener {
            checkboxCallback?.callback(checkbox.isChecked)
        }
        this.checkbox = checkbox
        return checkbox
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        checkbox?.isEnabled = true
        checkbox?.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        checkbox?.isEnabled = false
        checkbox?.setBackgroundColorResourceId(context, formStyleBundle.disabledBackgroundColor)
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).disabledBackgroundColor
        )
    }
}