package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator

class RadioButtonsElement<T>(
    private val value: T? = null,
    private var availableValues: List<T>,
    private val valueCallback: ValueCallback<T>? = null,
    formValidators: MutableList<FormValidator<T>> = ArrayList(),
    private val groupComponent: Int = R.layout.form_group_item_padding,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementValidatable<T>(formValidators) {

    private var radioGroup: RadioGroup? = null
    private var viewGroup: ViewGroup? = null

    override fun hide() {
        viewGroup?.visibility = View.GONE
    }

    override fun show() {
        viewGroup?.visibility = View.VISIBLE
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val radioGroup = RadioGroup(context)
        for ((i, availableValue) in availableValues.withIndex()) {
            val radioButton = AppCompatRadioButton(context)
            val params = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                if (i == 0) {
                    0
                } else {
                    40
                }, 20, 0, 20
            )
            radioButton.layoutParams = params
            radioButton.id = i
            radioButton.text = availableValue.toString()
            if (value == availableValue) {
                radioButton.isChecked = true
            }
            radioButton.setTextColor(
                ContextCompat.getColor(
                    context,
                    formStyleBundle.primaryTextColor
                )
            )
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked, android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                    intArrayOf(-android.R.attr.state_checked, -android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_checked, -android.R.attr.state_enabled)
                ), intArrayOf(
                    ContextCompat.getColor(context, formStyleBundle.primaryTextColor),
                    ContextCompat.getColor(context, formStyleBundle.colorAccent),
                    ColorUtils.blendARGB(
                        ContextCompat.getColor(
                            context,
                            formStyleBundle.primaryTextColor
                        ),
                        Color.BLACK,
                        0.4f
                    ),
                    ColorUtils.blendARGB(
                        ContextCompat.getColor(
                            context,
                            formStyleBundle.colorAccent
                        ),
                        Color.BLACK,
                        0.4f
                    )
                )
            )


            radioButton.buttonTintList = colorStateList //set the color tint list

            radioGroup.addView(radioButton)
        }
        radioGroup.orientation = LinearLayout.HORIZONTAL
        radioGroup.setOnCheckedChangeListener { group: RadioGroup, checkedId: Int ->
            valueCallback?.callback(availableValues.get(checkedId))
        }
        view.addView(radioGroup)
        this.radioGroup = radioGroup
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        viewGroup = view
        return view
    }

    override fun getVal(): T? {
        return radioGroup?.checkedRadioButtonId?.let {
            if (it >= availableValues.size) {
                return null
            }
            availableValues.get(it)
        }
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        val correctStyleBundle = this.formStyleBundle ?: formStyleBundle
        radioGroup?.isEnabled = true
        for (withIndex in availableValues.withIndex()) {
            val radioButton = radioGroup?.findViewById<RadioButton>(withIndex.index)
            radioButton?.isEnabled = true
        }
        viewGroup?.setBackgroundColorResourceId(
            context, correctStyleBundle.secondaryBackgroundColor
        )

    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        val correctStyleBundle = this.formStyleBundle ?: formStyleBundle
        radioGroup?.isEnabled = false
        for (withIndex in availableValues.withIndex()) {
            val radioButton = radioGroup?.findViewById<RadioButton>(withIndex.index)
            radioButton?.isEnabled = false
        }
        viewGroup?.setBackgroundColorResourceId(context, correctStyleBundle.disabledBackgroundColor)
    }
}