package cz.qase.android.formbuilderlibrary.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R


class SpinnerAdapter<T>(context: Context,
                        private val itemList: List<T>,
                        private val formStyleBundle: FormStyleBundle,
                        private val itemComponent: Int = R.layout.form_spinner_item
) : ArrayAdapter<T>(context, itemComponent, itemList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getOrCreateView(convertView, position)
    }

    override fun getDropDownView(position: Int, convertView: View?,
                                 parent: ViewGroup): View {
        return getOrCreateView(convertView, position)
    }

    private fun getOrCreateView(convertView: View?, position: Int): TextView {
        if (convertView != null) {
            (convertView as TextView).text = itemList[position].toString()
            return convertView
        }
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(itemComponent, null) as TextView
        view.setTextColorResourceId(context, formStyleBundle.secondaryTextColor)
        view.setBackgroundColorResourceId(context, formStyleBundle.secondaryBackgroundColor)
        view.text = itemList[position].toString()
        return view
    }
}