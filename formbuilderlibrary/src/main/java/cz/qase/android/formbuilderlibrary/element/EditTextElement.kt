package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator


open class EditTextElement(
    protected val hint: String?,
    protected val text: String? = null,
    private val groupComponent: Int = R.layout.form_group_item_padding,
    private val valueChangeListener: ValueCallback<String>? = null,
    protected val password: Boolean = false,
    formValidators: MutableList<FormValidator<String>> = ArrayList(),
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementValidatable<String>(formValidators) {

    private var editText: TextInputEditText? = null
    private var textInputLayout: TextInputLayout? = null
    private var viewGroup: ViewGroup? = null

    override fun hide() {
        textInputLayout?.visibility = View.GONE
    }

    override fun show() {
        textInputLayout?.visibility = View.VISIBLE
    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        textInputLayout = TextInputLayout(context)
        textInputLayout?.isErrorEnabled = true

        editText = TextInputEditText(context)
        editText?.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        if (password) {
            editText?.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
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
        view.addView(textInputLayout)
        return view
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

    override fun getVal(): String? = editText?.text.toString()

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        editText?.isEnabled = true
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        editText?.isEnabled = false
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).disabledBackgroundColor
        )
    }

}