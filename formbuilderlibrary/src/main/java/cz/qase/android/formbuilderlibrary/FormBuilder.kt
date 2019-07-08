package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.Divider
import cz.qase.android.formbuilderlibrary.element.SpaceElement
import cz.qase.android.formbuilderlibrary.element.generic.FormElement
import java.util.UUID

open class FormBuilder {

    private val elementsMap: LinkedHashMap<String, List<FormElement<*>>> = LinkedHashMap()

    fun addElement(formElement: FormElement<*>, addDivider: Boolean = false, id: String = UUID.randomUUID().toString()): FormBuilder {
        val elements: MutableList<FormElement<*>> = ArrayList()
        elements.add(formElement)

        if (addDivider) {
            elements.add(Divider())
        }
        elementsMap[id] = elements
        return this
    }

    fun addDivider(id: String = UUID.randomUUID().toString()): FormBuilder {
        elementsMap[id] = arrayListOf(Divider())
        return this
    }

    fun addSpace(id: String = UUID.randomUUID().toString()): FormBuilder {
        elementsMap[id] = arrayListOf(SpaceElement())
        return this
    }

    fun buildForm(context: Context,
                  containerView: ViewGroup,
                  formStyleBundle: FormStyleBundle = FormStyleBundle()): Form {
        return Form(context, containerView, elementsMap, formStyleBundle)
    }
}