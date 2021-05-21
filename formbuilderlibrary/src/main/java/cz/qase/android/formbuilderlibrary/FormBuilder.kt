package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.Divider
import cz.qase.android.formbuilderlibrary.element.SpaceElement
import cz.qase.android.formbuilderlibrary.element.generic.FormElement
import java.util.UUID

open class FormBuilder {

    private val elementsMap: LinkedHashMap<String, FormElementWrapper> = LinkedHashMap()

    fun addElement(
        formElement: FormElement<*>,
        addDivider: Boolean = false,
        id: String = UUID.randomUUID().toString(),
        groupId: String? = null
    ): FormBuilder {
        val elements: MutableList<FormElement<*>> = ArrayList()
        elements.add(formElement)

        if (addDivider) {
            elements.add(Divider())
        }
        elementsMap[id] = FormElementWrapper(elements, groupId)
        return this
    }

    fun addSpace(
        id: String = UUID.randomUUID().toString(), groupId: String? = null
    ): FormBuilder {
        elementsMap[id] = FormElementWrapper(arrayListOf(SpaceElement()), groupId)
        return this
    }

    fun buildForm(
        context: Context,
        containerView: ViewGroup,
        formStyleBundle: FormStyleBundle = FormStyleBundle()
    ): Form {
        return Form(context, containerView, elementsMap, formStyleBundle)
    }
}