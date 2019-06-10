package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator

open class EditTextElement(
        protected val hint: String?,
        protected val text: String? = null,
        private val valueChangeListener: ValueCallback<String>? = null,
        formValidators: MutableList<FormValidator<String>> = ArrayList()) : FormElementValidatable<String>(formValidators) {



    var editText: TextInputEditText? = null
    var textInputLayout: TextInputLayout? = null

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        textInputLayout = TextInputLayout(context)
        textInputLayout?.isErrorEnabled = true
        textInputLayout?.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        textInputLayout?.setPadding(10, 0, 10, 0)
        editText = TextInputEditText(context)
        editText?.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        textInputLayout?.addView(editText)
        textInputLayout?.hint = hint
        editText?.setText(text)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                positiveValidation()
                valueChangeListener?.callback(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        return textInputLayout!!
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

    public override fun enable() {
        super.disable()
        editText?.isEnabled = true
    }

    public override fun disable() {
        super.disable()
        editText?.isEnabled = false
    }
    override fun getVal(): String? = editText?.text.toString()

}