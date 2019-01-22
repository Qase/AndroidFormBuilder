package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.Divider
import cz.qase.android.formbuilderlibrary.element.generic.FormElement

open class FormBuilder {

    private val elements: MutableList<FormElement<*>> = ArrayList()

    fun addElement(formElement: FormElement<*>, addDivider: Boolean = false) {
        elements.add(formElement)

        if (addDivider){
            addDivider()
        }
    }

    fun addDivider() {
        elements.add(Divider())
    }

    fun buildForm(context: Context,
                  containerView: ViewGroup,
                  formStyleBundle: FormStyleBundle = FormStyleBundle()): Form {
        return Form(context, containerView, elements, formStyleBundle)
    }
}