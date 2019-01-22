package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue

class OpenableHeaderTextElement(private val label: String,
                                private val value: String,
                                private val groupComponent: Int = R.layout.form_group_item,
                                private val headerGroupComponent: Int = R.layout.form_group_item_inline,
                                private val headerComponent: Int = R.layout.form_inline_label,
                                private val symbolComponent: Int = R.layout.form_openable_symbol,
                                private val textComponent: Int = R.layout.form_text_item,
                                private val formStyleBundle: FormStyleBundle? = null) : FormElementNoValue() {

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val textView = prepareText(inflater, context, this.formStyleBundle ?: formStyleBundle, view)
        textView.visibility = View.GONE
        val headerGroup = inflater.inflate(headerGroupComponent, view, false) as ViewGroup
        val headerView = prepareLabel(inflater, context, this.formStyleBundle
                ?: formStyleBundle, headerGroup)
        val symbolWrapper = LinearLayout(context)
        symbolWrapper.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        symbolWrapper.orientation = LinearLayout.HORIZONTAL
        symbolWrapper.gravity = Gravity.END

        val symbolView = inflater.inflate(symbolComponent, symbolWrapper, false) as ImageView
        val symbolColor = ContextCompat.getColor(context, formStyleBundle.secondaryTextColor)
        symbolView.setColorFilter(symbolColor)
        symbolWrapper.addView(symbolView)
        headerGroup.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        headerGroup.addView(headerView)
        headerGroup.addView(symbolWrapper)

        headerGroup.isClickable = true
        headerGroup.setOnClickListener {
            if (textView.visibility == View.GONE) {
                textView.visibility = View.VISIBLE
                symbolView.rotation = symbolView.rotation + 180
            } else {
                textView.visibility = View.GONE
                symbolView.rotation = symbolView.rotation + 180
            }
        }
        headerGroup.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    headerGroup.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
                    headerGroup.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    headerGroup.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
                }
                MotionEvent.ACTION_DOWN -> {
                    headerGroup.setBackgroundColorResourceId(context, formStyleBundle.primaryBackgroundColor)
                }
            }
            true
        }

        view.addView(headerGroup)
        view.addView(textView)
        return view
    }

    private fun prepareText(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        textView.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        textView.text = value
        return textView
    }

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, formStyleBundle.primaryTextColor)
        headerView.text = label
        return headerView
    }
}
