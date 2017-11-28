package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.FormElement

open class FormBuilder {

    private val elements: MutableList<FormElement<*>> = ArrayList()

    fun addElement(formElement: FormElement<*>) {
        elements.add(formElement)
    }

    fun buildForm(context: Context,
                  containerView: ViewGroup,
                  formStyleBundle: FormStyleBundle = FormStyleBundle()): Form {
        return Form(context, containerView, elements, formStyleBundle)
    }
}