package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.FormElement

class Form(val context: Context,
           val viewGroup: ViewGroup,
           elements: MutableList<FormElement<*>>,
           val formStyleBundle: FormStyleBundle) {

    val elementsMap: LinkedHashMap<String, FormElement<*>> = LinkedHashMap()

    init {
        for (element in elements) {
            elementsMap.put(element.key, element)
        }
    }

    @Throws(ValidationException::class)
    fun validate() {
        for (formElement in elementsMap.values) {
            formElement.validate()
        }
    }

    fun draw() {
        for (element in elementsMap.values) {
            viewGroup.addView(element.createView(context, formStyleBundle))
        }
    }

    fun sendForm(callback: Callback) {
        try {
            validate()
        } catch (e: ValidationException) {
            callback.errorCallback(e.message)
            return
        }
        callback.successCallback()
    }

    interface Callback {
        fun successCallback()
        fun errorCallback(message: String?)

    }
}