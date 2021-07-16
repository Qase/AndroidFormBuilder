package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid

class LabelSwitchElement(
    private val label: String,
    private var initialValue: Boolean,
    private val checkboxCallback: CheckboxCallback? = null,
    private val trueNote: String? = null,
    private val falseNote: String? = null,
    private val groupComponent: Int = R.layout.form_group_item_inline,
    private val headerComponent: Int = R.layout.form_inline_label,
    private val noteSymbolComponent: Int = R.layout.form_note_symbol,
    private val switchComponent: Int = R.layout.form_inline_switch,
    private val groupComponentEnd: Int = R.layout.form_group_item_inline_end,
    private val noteCallback: ActionCallback? = null,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementValid<Boolean>() {

    private var switchView: SwitchCompat? = null
    private var viewGroup: ViewGroup? = null
    private var headerView: TextView? = null

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
        val groupEnd = inflater.inflate(groupComponentEnd, view, false) as ViewGroup
        val headerView = prepareHeader(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        val switchView = prepareSwitch(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, groupEnd
        )
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(headerView)
        noteCallback?.let { actionCallback ->
            val noteView = inflater.inflate(noteSymbolComponent, groupEnd, false) as ImageView
            groupEnd.addView(noteView)
            val neutralColor = ContextCompat.getColor(context, formStyleBundle.neutralSymbolColor)
            val primaryColor = ContextCompat.getColor(context, formStyleBundle.primaryTextColor)
            noteView.setColorFilter(neutralColor)
            noteView.setOnClickListener { actionCallback.callback() }
            noteView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        noteView.setColorFilter(neutralColor)
                        noteView.performClick()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        noteView.setColorFilter(neutralColor)
                    }
                    MotionEvent.ACTION_DOWN -> {

                        noteView.setColorFilter(primaryColor)
                    }
                }
                true
            }
        }
        groupEnd.addView(switchView)
        view.addView(groupEnd)
        viewGroup = view
        return view
    }

    private fun prepareSwitch(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): SwitchCompat {
        val tmpSwitchView = inflater.inflate(switchComponent, root, false) as SwitchCompat
        tmpSwitchView.isChecked = initialValue
        tmpSwitchView.setColor(formStyleBundle.primaryTextColor, context)
        tmpSwitchView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        tmpSwitchView.setOnCheckedChangeListener { _, isChecked ->
            checkboxCallback?.callback(isChecked)
            headerView?.let {
                setLabelText(it, isChecked)
            }
        }
        switchView = tmpSwitchView
        return tmpSwitchView
    }

    private fun prepareHeader(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        setLabelText(headerView, initialValue)
        this.headerView = headerView
        return headerView
    }

    private fun setLabelText(headerView: TextView, value: Boolean) {
        if (value) {
            if (trueNote != null) {
                headerView.text = label + "\n" + trueNote
            } else {
                headerView.text = label
            }
        } else {
            if (falseNote != null) {
                headerView.text = label + "\n" + falseNote
            } else {
                headerView.text = label
            }
        }
    }

    private fun SwitchCompat.setColor(colorRes: Int, context: Context) {
        // trackColor is the thumbColor with 30% transparency (77)
        val color = ContextCompat.getColor(context, colorRes)
        val trackColor = Color.argb(77, Color.red(color), Color.green(color), Color.blue(color))

        // setting the thumb color
        DrawableCompat.setTintList(
            thumbDrawable, ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
                intArrayOf(color, Color.WHITE)
            )
        )

        // setting the track color
        DrawableCompat.setTintList(
            trackDrawable, ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
                intArrayOf(
                    trackColor,
                    Color.parseColor("#4DFFFFFF") // full black with 30% transparency (4D)
                )
            )
        )
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        val correctStyleBundle = this.formStyleBundle ?: formStyleBundle
        switchView?.setColor(correctStyleBundle.primaryTextColor, context)
        switchView?.isEnabled = true
        headerView?.setTextColorResourceId(context, correctStyleBundle.primaryTextColor)
        viewGroup?.setBackgroundColorResourceId(
            context,
            correctStyleBundle.secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        val correctStyleBundle = this.formStyleBundle ?: formStyleBundle
        switchView?.setColor(correctStyleBundle.disabledPrimaryTextColor, context)
        switchView?.isEnabled = false
        headerView?.setTextColorResourceId(context, correctStyleBundle.disabledPrimaryTextColor)
        viewGroup?.setBackgroundColorResourceId(context, correctStyleBundle.disabledBackgroundColor)
    }
}
