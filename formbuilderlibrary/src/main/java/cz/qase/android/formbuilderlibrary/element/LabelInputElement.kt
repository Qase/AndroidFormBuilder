package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator


class LabelInputElement(private val label: String,
                        private var hint: String,
                        private var value: String,
                        private val valueChangeListener: ValueCallback<String>,
                        formValidators: MutableList<FormValidator<String>> = ArrayList(),
                        private val groupComponent: Int = R.layout.form_group_item_inline,
                        private val headerComponent: Int = R.layout.form_inline_label,
                        private val inputComponent: Int = R.layout.form_text_input_layout,
                        private val formStyleBundle: FormStyleBundle? = null) : FormElementValidatable<String>(formValidators) {
    override fun getVal(): String? {
        return value
    }

    private var labelView: TextView? = null
    private var textInputEditText: TextInputEditText? = null
    private var textInputLayout: TextInputLayout? = null

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareLabel(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val inputView = prepareText(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(headerView)
        view.addView(inputView)
        return view
    }

    private fun prepareText(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextInputLayout {
        val textInputLayout = inflater.inflate(inputComponent, root, false) as TextInputLayout
        textInputLayout.hint = hint
        textInputEditText = TextInputEditText(context)
        textInputLayout.addView(textInputEditText)
        textInputEditText?.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        textInputEditText?.setText(value)
        textInputEditText?.addTextChangedListener(
                object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        value = s.toString()
                        positiveValidation()
                        valueChangeListener.callback(s.toString())

                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    }

                    override fun afterTextChanged(s: Editable) {
                    }
                }
        )
        this.textInputLayout = textInputLayout
        return textInputLayout
    }

    fun updateLabel(label: String) {
        labelView?.text = label
    }

    fun updateText(text: String) {
        textInputEditText?.setText(text)
    }

    private fun positiveValidation() {
        try {
            super.validate()
            textInputLayout?.error = null
        } catch (e: ValidationException) {
        }
    }

    override fun validate() {
        try {
            super.validate()
            textInputLayout?.error = null
        } catch (e: ValidationException) {
            textInputLayout?.error = e.message
            throw e
        }
    }

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        labelView = headerView
        return headerView
    }
}