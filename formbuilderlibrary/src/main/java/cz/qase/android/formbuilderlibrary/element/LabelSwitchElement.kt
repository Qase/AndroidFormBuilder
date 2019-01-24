package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid
import android.content.res.ColorStateList
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.SwitchCompat
import android.graphics.Color
import android.support.v4.content.ContextCompat


class LabelSwitchElement(private val label: String,
                         private var checked: Boolean,
                         private val checkboxCallback: CheckboxCallback,
                         private val groupComponent: Int = R.layout.form_group_item_inline,
                         private val headerComponent: Int = R.layout.form_inline_label,
                         private val switchComponent: Int = R.layout.form_inline_switch,
                         private val formStyleBundle: FormStyleBundle? = null) : FormElementValid<Boolean>() {
    override fun getVal(): Boolean {
        return checked
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


    private fun prepareSwitch(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): SwitchCompat {
        val switchView = inflater.inflate(switchComponent, root, false) as SwitchCompat
        switchView.isChecked = checked
        switchView.setColor(formStyleBundle.primaryBackgroundColor, context)
        switchView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        switchView.setOnCheckedChangeListener { _, isChecked ->
            checked = isChecked
            checkboxCallback.callback(checked)
        }
        return switchView
    }

    private fun prepareHeader(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        return headerView
    }


    private fun SwitchCompat.setColor(colorRes: Int, context: Context){
        // trackColor is the thumbColor with 30% transparency (77)

        val color = ContextCompat.getColor(context, colorRes)

        val trackColor = Color.argb(77, Color.red(color), Color.green(color), Color.blue(color))

        // setting the thumb color
        DrawableCompat.setTintList(thumbDrawable, ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
                intArrayOf(color, Color.WHITE)))

        // setting the track color
        DrawableCompat.setTintList(trackDrawable, ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
                intArrayOf(trackColor, Color.parseColor("#4D000000") // full black with 30% transparency (4D)
                )))
    }
}
