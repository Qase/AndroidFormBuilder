package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator
import org.angmarch.views.NiceSpinner

class LabelSpinnerElement<T>(
    private val label: String,
    private val value: T? = null,
    private var availableValues: List<T>,
    private val valueCallback: ValueCallback<T>? = null,
    formValidators: MutableList<FormValidator<T>> = ArrayList(),
    private val groupComponent: Int = R.layout.form_group_item_inline,
    private val labelComponent: Int = R.layout.form_inline_label,
    private val spinnerComponent: Int = R.layout.form_inline_spinner,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementValidatable<T>(formValidators) {

    var spinner: NiceSpinner? = null
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
        val headerView = prepareLabel(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        val spinnerView = prepareSpinner(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(headerView)
        view.addView(spinnerView)
        viewGroup = view
        return view
    }

    private fun prepareSpinner(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): NiceSpinner {
        val spinner = inflater.inflate(spinnerComponent, root, false) as NiceSpinner
        spinner.attachDataSource(availableValues)
        if (value != null) {
            spinner.selectedIndex = availableValues.indexOf(value)
        }
        spinner.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                positiveValidation()
                valueCallback?.callback(availableValues[position])
            }
        })
        this.spinner = spinner
        return spinner
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

    private fun positiveValidation() {
        try {
            super.validate()
            spinner?.error = null
        } catch (e: ValidationException) {
        }
    }

    override fun validate() {
        try {
            super.validate()
            spinner?.error = null
        } catch (e: ValidationException) {
            spinner?.error = e.message
            throw e
        }
    }

    fun updateOptions(availableValues: List<T>) {
        this.availableValues = availableValues
        spinner?.attachDataSource(availableValues)
    }

    override fun getVal(): T? {
        return spinner?.selectedIndex?.let {
            if (it >= availableValues.size) {
                return null
            }
            availableValues.get(it)
        }
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        spinner?.isEnabled = true
        spinner?.isClickable = true
        spinner?.isFocusable = true
        spinner?.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        spinner?.isEnabled = false
        spinner?.isClickable = false
        spinner?.isFocusable = false
        spinner?.setBackgroundColorResourceId(context, formStyleBundle.disabledBackgroundColor)
        viewGroup?.setBackgroundColorResourceId(
            context, (this.formStyleBundle
                ?: formStyleBundle).disabledBackgroundColor
        )
    }
}