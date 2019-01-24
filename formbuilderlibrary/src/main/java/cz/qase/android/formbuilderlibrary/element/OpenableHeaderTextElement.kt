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
                                private val values: List<String>,
                                private val groupComponent: Int = R.layout.form_group_item,
                                private val headerGroupComponent: Int = R.layout.form_group_item_inline,
                                private val headerComponent: Int = R.layout.form_inline_label,
                                private val symbolComponent: Int = R.layout.form_openable_symbol,
                                private val textComponent: Int = R.layout.form_text_item,
                                private var formStyleBundle: FormStyleBundle? = null
) : FormElementNoValue() {

    private lateinit var context: Context
    private lateinit var fsb: FormStyleBundle

    private lateinit var textViewWrapper: LinearLayout
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
        if (textViewWrapper.visibility == View.GONE) {
            textViewWrapper.visibility = View.VISIBLE
            symbolView.rotation = 180f
        } else {
            textViewWrapper.visibility = View.GONE
            symbolView.rotation = 0f
        }
    }


    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        fsb = this.formStyleBundle ?: formStyleBundle
        this.context = context

        val view = inflater.inflate(groupComponent, null) as ViewGroup
        headerGroup = prepareHeaderGroup(inflater, view)
        textViewWrapper = prepareTextViewWrapper(inflater, view)
        val headerView = prepareLabel(inflater, headerGroup)
        symbolWrapper = prepareSymbol(inflater, headerGroup)

        headerGroup.addView(headerView)
        headerGroup.addView(symbolWrapper)

        view.addView(headerGroup)
        view.addView(textViewWrapper)
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

    private fun prepareTextViewWrapper(inflater: LayoutInflater, root: ViewGroup): LinearLayout {
        val textWrapper = LinearLayout(context)
        textWrapper.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textWrapper.orientation = LinearLayout.VERTICAL
        textWrapper.gravity = Gravity.END
        textWrapper.visibility = View.GONE

        for (text in values){
            val textView = prepareText(inflater, textWrapper, text)
            textWrapper.addView(textView)
        }
        return textWrapper
    }

    private fun prepareText(inflater: LayoutInflater, root: ViewGroup, text: String): TextView {
        val textView = inflater.inflate(textComponent, root, false) as TextView
        textView.setTextColorResourceId(context, fsb.secondaryTextColor)
        textView.setBackgroundColorResourceId(context, fsb.secondaryBackgroundColor)
        textView.text = text
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
        val symbolColor = ContextCompat.getColor(context, fsb.primaryBackgroundColor)
        symbolView.setColorFilter(symbolColor)
        symbolWrapper.addView(symbolView)
        return symbolWrapper
    }
}
