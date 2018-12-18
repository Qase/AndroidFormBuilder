package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.element.generic.FormElementValid
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import org.angmarch.views.NiceSpinner

class LabelSpinnerElement<T>(private val label: String,
                             private var value: T,
                             private var availableValues: List<T>,
                             private val valueCallback: ValueCallback<T>,
                             private val groupComponent: Int = R.layout.form_group_item_inline,
                             private val labelComponent: Int = R.layout.form_inline_label,
                             private val spinnerComponent: Int = R.layout.form_inline_spinner,
                             private val itemComponent: Int = R.layout.form_spinner_item,
                             private val arrowDrawable: Int = R.drawable.ic_arrow_down,
                             private val formStyleBundle: FormStyleBundle? = null
) : FormElementValid<T>() {

    var spinner: NiceSpinner? = null

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(groupComponent, null) as ViewGroup
        val headerView = prepareLabel(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        val spinnerView = prepareSpinner(inflater, context, this.formStyleBundle
                ?: formStyleBundle, view)
        view.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        view.addView(headerView)
        view.addView(spinnerView)
        return view
    }

    private fun prepareSpinner(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): NiceSpinner {
        val spinner = inflater.inflate(spinnerComponent, root, false) as NiceSpinner
        spinner.setArrowDrawable(context.resources.getDrawable(arrowDrawable))
        spinner.setArrowTintColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        spinner.attachDataSource(availableValues)
        spinner.setTextColor(context.resources.getColor(formStyleBundle.secondaryTextColor))
        spinner.addOnItemClickListener { adapterView, view, i, l ->

        }
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                valueCallback.callback(availableValues[position])
            }
        })
        this.spinner = spinner
        return spinner
    }

    private fun prepareLabel(inflater: LayoutInflater, context: Context, formStyleBundle: FormStyleBundle, root: ViewGroup): TextView {
        val headerView = inflater.inflate(labelComponent, root, false) as TextView
        headerView.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        headerView.text = label
        return headerView
    }

    override fun getVal(): T? {
        return spinner?.selectedIndex?.let { availableValues.get(it) }
    }
}