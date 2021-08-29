package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class TextAreaElement(
    private val hint: String?,
    private var value: String? = null,
    private val valueChangeListener: ValueCallback<String>? = null,
    private val numberOfLines: Int = 3,
    private val maxLength: Int? = null,
    formValidators: MutableList<FormValidator<String>> = ArrayList(),
    private val groupComponent: Int = R.layout.form_group_item_padding,
    private val textAreaComponent: Int = R.layout.form_textarea_item,
    private val footerComponent: Int = R.layout.form_footer_text_right,
    private val validateImmediately: Boolean = false,
    private var formStyleBundle: FormStyleBundle? = null
) : FormElementValidatable<String>(formValidators) {

    override fun getVal(): String? {
        return textInputEditText?.text.toString()
    }

    private var footerView: TextView? = null
    private var textInputEditText: TextInputEditText? = null
    private var textInputLayout: TextInputLayout? = null
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
        val inputView = prepareText(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        val footerView = prepareFooter(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(inputView)
        view.addView(footerView)
        viewGroup = view
        return view
    }

    private fun prepareText(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): TextInputLayout {
        val textInputLayout = inflater.inflate(textAreaComponent, root, false) as TextInputLayout
        textInputEditText = TextInputEditText(context)
        textInputLayout.addView(textInputEditText)
        textInputEditText?.minLines = numberOfLines

        if (maxLength != null) {
            textInputEditText?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }

        textInputEditText?.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        textInputEditText?.setText(value)
        textInputEditText?.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val length = getVal()?.length ?: 0
                    if (maxLength != null) {
                        footerView?.text = "$length/$maxLength"
                    } else {
                        footerView?.text = "$length"
                    }
                    if (validateImmediately) {
                        validation()
                    } else {
                        positiveValidation()
                    }
                    valueChangeListener?.callback(s.toString())

                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                }
            }
        )
        if (hint?.length ?: 0 > 20) {
            textInputEditText?.setHint(hint)
        } else {
            textInputLayout.hint = hint
        }
        this.textInputLayout = textInputLayout
        return textInputLayout
    }

    private fun prepareFooter(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): TextView {
        val footerView = inflater.inflate(footerComponent, root, false) as TextView
        if (maxLength != null) {
            footerView.text = "0/$maxLength"
        } else {
            footerView.text = "0"
        }
        footerView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        this.footerView = footerView
        return footerView

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

    private fun validation() {
        try {
            super.validate()
            textInputLayout?.error = null
        } catch (e: ValidationException) {
            textInputLayout?.error = e.message
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

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        textInputEditText?.isEnabled = true
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        textInputEditText?.isEnabled = false
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).disabledBackgroundColor
        )
    }
}