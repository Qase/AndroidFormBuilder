package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValidatable
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.FormValidator
import org.joda.time.DateTime
import wtf.qase.datetimepicker.DateTimePickerDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LabelDateTimeElement(private val label: String,
                           private var hint: String,
                           private var supportFragmentManager: FragmentManager,
                           private val valueChangeListener: ValueCallback<DateTime>,
                           private var value: DateTime? = null,
                           private var sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault()),
                           formValidators: MutableList<FormValidator<DateTime>> = ArrayList(),
                           private val groupComponent: Int = R.layout.form_group_item_inline,
                           private val headerComponent: Int = R.layout.form_inline_label,
                           private val textComponent: Int = R.layout.form_inline_text,
                           private val formStyleBundle: FormStyleBundle? = null) : FormElementValidatable<DateTime>(formValidators) {

    override fun getVal(): DateTime? {
        return value
    }

    private var textView: TextView? = null
    private var labelView: TextView? = null
    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareLabel(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val textView = prepareText(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)

        view.addView(headerView)
        view.addView(textView)
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
                    textView.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
                }
                MotionEvent.ACTION_DOWN -> {
                    view.setBackgroundColorResourceId(context, formStyleBundle.primaryBackgroundColor)
                }
            }
            true
        }
        return view
    }

    private fun prepareText(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        textView.text = if (value != null) {
            sdf.format(value)
        } else {
            hint
        }
        val callback: (date: Date) -> Unit = { newDate ->
            val dateTime = DateTime(newDate)
            value = dateTime
            textView.text = sdf.format(newDate)
            valueChangeListener.callback(dateTime)
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

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        labelView = headerView
        return headerView
    }
}