package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.common.setBackgroundColorResourceId
import cz.qase.android.formbuilderlibrary.common.setTextColorResourceId
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback
import cz.qase.android.formbuilderlibrary.element.generic.FormElementNoValue

open class NavigationWithErrorElement(
    private val actionCallback: ActionCallback,
    private val label: String,
    private val groupComponent: Int = R.layout.form_group_item_inline,
    private val headerComponent: Int = R.layout.form_inline_label,
    private val warningSymbolComponent: Int = R.layout.form_error_symbol,
    private val symbolComponent: Int = R.layout.form_navigation_symbol,
    private val groupComponentEnd: Int = R.layout.form_group_item_inline_end,
    private val formStyleBundle: FormStyleBundle? = null
) : FormElementNoValue() {

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
        val viewEnd = inflater.inflate(groupComponentEnd, view, false) as ViewGroup
        val headerView = prepareLabel(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, view
        )
        val errorView = inflater.inflate(warningSymbolComponent, viewEnd, false) as ImageView
        val symbolView = prepareSymbol(
            inflater, context, this.formStyleBundle
                ?: formStyleBundle, viewEnd
        )
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.addView(headerView)
        viewEnd.addView(errorView)
        viewEnd.addView(symbolView)
        view.addView(viewEnd)

        view.isClickable = true
        view.setOnClickListener {
            actionCallback.callback()
        }
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.setBackgroundColorResourceId(
                        context,
                        formStyleBundle.secondaryBackgroundColor
                    )
                }
                MotionEvent.ACTION_DOWN -> {
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

    private fun prepareSymbol(
        inflater: LayoutInflater,
        context: Context,
        formStyleBundle: FormStyleBundle,
        root: ViewGroup
    ): ImageView {
        val symbolView = inflater.inflate(symbolComponent, root, false) as ImageView
        val color = ContextCompat.getColor(context, formStyleBundle.primaryTextColor)
        symbolView.setColorFilter(color)
        return symbolView
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
        return headerView
    }
}