package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.Divider
import cz.qase.android.formbuilderlibrary.element.SpaceElement
import cz.qase.android.formbuilderlibrary.element.generic.FormElement

open class FormBuilder {

    private val elements: MutableList<FormElement<*>> = ArrayList()

    fun addElement(formElement: FormElement<*>, addDivider: Boolean = false): FormBuilder {
        elements.add(formElement)

        if (addDivider) {
            addDivider()
        }
        return this
    }

    fun addDivider(): FormBuilder {
        elements.add(Divider())
        return this
    }

    fun addSpace(): FormBuilder {
        elements.add(SpaceElement())
        return this
    }


    fun buildForm(context: Context,
                  containerView: ViewGroup,
                  formStyleBundle: FormStyleBundle = FormStyleBundle()): Form {
        return Form(context, containerView, elements, formStyleBundle)
    }
}