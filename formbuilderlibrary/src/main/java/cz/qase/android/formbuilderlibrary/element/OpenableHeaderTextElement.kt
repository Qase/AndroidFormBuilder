package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.*
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
                                private var formStyleBundle: FormStyleBundle? = null
) : FormElementNoValue() {

    private lateinit var context: Context
    private lateinit var fsb: FormStyleBundle

    private lateinit var textView: TextView
    private lateinit var headerGroup: ViewGroup
    private lateinit var symbolWrapper: LinearLayout
    private lateinit var symbolView: ImageView


    private val headerTouchAction = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                headerGroup.setBackgroundColorResourceId(context, fsb.secondaryBackgroundColor)
                headerGroup.performClick()
            }
            MotionEvent.ACTION_CANCEL -> {
                headerGroup.setBackgroundColorResourceId(context, fsb.secondaryBackgroundColor)
            }
            MotionEvent.ACTION_DOWN -> {
                headerGroup.setBackgroundColorResourceId(context, fsb.primaryBackgroundColor)
            }
        }
        true
    }

    private val headerClickAction = View.OnClickListener {
        if (textView.visibility == View.GONE) {
            textView.visibility = View.VISIBLE
            symbolView.rotation = symbolView.rotation + 180
        } else {
            textView.visibility = View.GONE
            symbolView.rotation = symbolView.rotation + 180
        }
    }


    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        fsb = this.formStyleBundle ?: formStyleBundle
        this.context = context

        val view = inflater.inflate(groupComponent, null) as ViewGroup
        headerGroup = prepareHeaderGroup(inflater, view)
        textView = prepareText(inflater, view)
        val headerView = prepareLabel(inflater, headerGroup)
        symbolWrapper = prepareSymbol(inflater, headerGroup)

        headerGroup.addView(headerView)
        headerGroup.addView(symbolWrapper)

        view.addView(headerGroup)
        view.addView(textView)
        return view
    }

    private fun prepareHeaderGroup(inflater: LayoutInflater, root: ViewGroup): ViewGroup {
        val headerGroup = inflater.inflate(headerGroupComponent, root, false) as ViewGroup
        headerGroup.setBackgroundColorResourceId(context, fsb.secondaryBackgroundColor)
        headerGroup.isClickable = true
        headerGroup.setOnClickListener(headerClickAction)
        headerGroup.setOnTouchListener(headerTouchAction)
        return headerGroup
    }

    private fun prepareText(inflater: LayoutInflater, root: ViewGroup): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColorResourceId(context, fsb.secondaryTextColor)
        textView.setBackgroundColorResourceId(context, fsb.secondaryBackgroundColor)
        textView.text = value
        textView.visibility = View.GONE
        return textView
    }

    private fun prepareLabel(inflater: LayoutInflater, root: ViewGroup): TextView {
        val headerView = inflater.inflate(headerComponent, root, false) as TextView
        headerView.setTextColorResourceId(context, fsb.primaryTextColor)
        headerView.text = label
        return headerView
    }

    private fun prepareSymbol(inflater: LayoutInflater, root: ViewGroup): LinearLayout {
        val symbolWrapper = LinearLayout(context)
        symbolWrapper.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        symbolWrapper.orientation = LinearLayout.HORIZONTAL
        symbolWrapper.gravity = Gravity.END

        symbolView = inflater.inflate(symbolComponent, symbolWrapper, false) as ImageView
        val symbolColor = ContextCompat.getColor(context, fsb.secondaryTextColor)
        symbolView.setColorFilter(symbolColor)
        symbolWrapper.addView(symbolView)
        return symbolWrapper
    }
}
