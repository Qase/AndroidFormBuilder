package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.FormElement

class Form(val context: Context,
           val viewGroup: ViewGroup,
           elements: MutableList<FormElement<*>>,
           val formStyleBundle: FormStyleBundle) {

    val elements: List<FormElement<*>> = arrayListOf()

    init {
        for (element in elements) {
            elements.add(element)
        }
    }

    @Throws(ValidationException::class)
    fun validate() {
        for (formElement in elements) {
            formElement.validate()
        }
    }

    fun draw() {
        for (element in elements) {
            viewGroup.addView(element.createView(context, formStyleBundle))
        }
    }

    fun reDraw() {
        viewGroup.removeAllViews()
        for (element in elements) {
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