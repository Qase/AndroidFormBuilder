package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid

class LabelSwitchElement(private val label: String,
                         private var initialValue: Boolean,
                         private val checkboxCallback: CheckboxCallback,
                         private val groupComponent: Int = R.layout.form_group_item_inline,
                         private val headerComponent: Int = R.layout.form_inline_label,
                         private val switchComponent: Int = R.layout.form_inline_switch,
                         private val formStyleBundle: FormStyleBundle? = null) : FormElementValid<Boolean>() {

    private var switchView: SwitchCompat? = null
    private var viewGroup: ViewGroup? = null

    override fun hide() {
        viewGroup?.visibility = View.GONE
    }

    override fun show() {
        viewGroup?.visibility = View.VISIBLE
    }

    override fun getVal(): Boolean {
        return switchView?.isChecked == true
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
        viewGroup = view
        return view
    }

    private fun prepareSwitch(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): SwitchCompat {
        val tmpSwitchView = inflater.inflate(switchComponent, root, false) as SwitchCompat
        tmpSwitchView.isChecked = initialValue
        tmpSwitchView.setColor(formStyleBundle.primaryTextColor, context)
        tmpSwitchView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        tmpSwitchView.setOnCheckedChangeListener { _, isChecked ->
            checkboxCallback.callback(isChecked)
        }
        switchView = tmpSwitchView
        return tmpSwitchView
    }

    private fun prepareHeader(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        return headerView
    }


    private fun SwitchCompat.setColor(colorRes: Int, context: Context) {
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

    public override fun enable() {
        super.enable()
        switchView?.isEnabled = true
    }

    public override fun disable() {
        super.disable()
        switchView?.isEnabled = false
    }
}
