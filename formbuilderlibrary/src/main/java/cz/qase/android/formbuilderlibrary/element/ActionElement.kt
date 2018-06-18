package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R

class ActionElement(private val label: String,
                    private val actionCallback: ActionCallback,
                    private val component: Int = R.layout.form_action) : FormElementValid<Unit>() {

    override fun getVal() {

    }

    override fun createView(context: Context, formStyleBundle: FormStyleBundle): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val button = inflater.inflate(component, null) as Button
        button.text = label
        button.setBackgroundColor(context.resources.getColor(formStyleBundle.secondaryBackgroundColor))
        button.setTextColor(context.resources.getColor(formStyleBundle.primaryTextColor))
        button.setOnClickListener({
            actionCallback.callback()
        })
        return button
    }

    interface ActionCallback {
        fun callback()
    }
}
