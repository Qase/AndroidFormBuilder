package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator
import wtf.qase.datetimepicker.DateTimePickerDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LabelDateTimeElement(
    private val label: String,
    private var hint: String,
    private var supportFragmentManager: FragmentManager,
    private val valueChangeListener: ValueCallback<Date>? = null,
    private var value: Date? = null,
    private var sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault()),
    formValidators: MutableList<FormValidator<Date>> = ArrayList(),
    private val groupComponent: Int = R.layout.form_group_item_inline,
    private val headerComponent: Int = R.layout.form_inline_label,
    private val textComponent: Int = R.layout.form_inline_text,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementValidatable<Date>(formValidators) {

    override fun getVal(): Date? {
        return value
    }

    private var textView: TextView? = null
    private var labelView: TextView? = null
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
        val textView = prepareText(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)

        view.addView(headerView)
        view.addView(textView)
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                    textView.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                }
                MotionEvent.ACTION_DOWN -> {
                    textView.requestFocus()
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.primaryBackgroundColor
                    )
                }
            }
            true
        }
        viewGroup = view
        return view
    }

    private fun prepareText(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        val tmpValue = value
        textView.text = if (tmpValue != null) {
            sdf.format(tmpValue)
        } else {
            hint
        }
        textView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    root.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                    textView.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    root.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                }
                MotionEvent.ACTION_DOWN -> {
                    textView.requestFocus()
                    root.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.primaryBackgroundColor
                    )
                }
            }
            true
        }
        val callback: (date: Date) -> Unit = { newDate ->
            value = newDate
            textView.text = sdf.format(newDate)
            positiveValidation()
            valueChangeListener?.callback(newDate)
        }
        textView.setOnClickListener {
            DateTimePickerDialog.show(
                supportFragmentManager,
                "fragment_datepicker",              //tag for fragment manager
                callback,                           //calback with selected date
                Date(),                             //current date
                DateTimePickerDialog.TIME_DATE      //choose one - DATE_TIME, TIME_ONLY, DATE_ONLY, TIME_DATE
            )
        }

        this.textView = textView
        return textView
    }

    fun updateLabel(label: String) {
        textView?.text = label
    }

    fun updateText(text: String) {
        textView?.text = text
    }

    private fun positiveValidation() {
        try {
            super.validate()
            textView?.error = null
        } catch (e: ValidationException) {
        }
    }

    override fun validate() {
        try {
            super.validate()
            textView?.error = null
        } catch (e: ValidationException) {
            textView?.text = e.message
            textView?.error = e.message
            throw e
        }
    }

    private fun prepareLabel(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        labelView = headerView
        return headerView
    }

    override fun enableElement(context: Context, formStyleBundle: FormStyleBundle) {
        val correctStyleBundle = this.formStyleBundle ?: formStyleBundle
        viewGroup?.isEnabled = true
        textView?.isEnabled = true
        textView?.setTextColorResourceId(context, correctStyleBundle.secondaryTextColor)
        labelView?.setTextColorResourceId(context, correctStyleBundle.primaryTextColor)
        viewGroup?.setBackgroundColorResourceId(
            context,
            correctStyleBundle.secondaryBackgroundColor
        )
    }

    override fun disableElement(context: Context, formStyleBundle: FormStyleBundle) {
        val correctStyleBundle = this.formStyleBundle ?: formStyleBundle
        viewGroup?.isEnabled = false
        textView?.isEnabled = false
        textView?.setTextColorResourceId(context, correctStyleBundle.disabledSecondaryTextColor)
        labelView?.setTextColorResourceId(context, correctStyleBundle.disabledPrimaryTextColor)
        viewGroup?.setBackgroundColorResourceId(context, correctStyleBundle.disabledBackgroundColor)
    }
}