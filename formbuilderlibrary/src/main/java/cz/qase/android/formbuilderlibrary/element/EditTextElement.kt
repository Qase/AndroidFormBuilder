package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.validator.FormValidator

open class EditTextElement(key: String,
                           protected val hint: String?,
                           protected val hintRes: Int?,
                           protected var text: String? = null,
                           formValidators: MutableList<FormValidator<String>> = ArrayList()) : FormElementValidatable<String>(key, formValidators) {

    constructor(key: String,
                hint: String?,
                text: String? = null,
                formValidators: MutableList<FormValidator<String>> = ArrayList()) : this(key, hint, null, text, formValidators)

    constructor(key: String,
                hintRes: Int?,
                text: String? = null,
                formValidators: MutableList<FormValidator<String>> = ArrayList()) : this(key, null, hintRes, text, formValidators)


    var editText: TextInputEditText? = null
    var textInputLayout: TextInputLayout? = null

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        textInputLayout = TextInputLayout(context)
        textInputLayout?.isErrorEnabled = true
        textInputLayout?.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))

        editText = TextInputEditText(context)
        editText?.setTextColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        textInputLayout?.addView(editText)
        textInputLayout?.hint = if (hintRes != null) {
            context.resources.getString(hintRes ?: 0)
        } else {
            hint
        }
        editText?.setText(text)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                text = s.toString()
                validate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        return textInputLayout!!
    }

    override fun validate() {
        super.validate()
        if (textInputLayout != null) {
            if (invalid) {
                textInputLayout?.error = invalidMessage
            } else {
                textInputLayout?.error = null
            }
        }
    }

    override fun getVal(): String? = editText?.text.toString()

}