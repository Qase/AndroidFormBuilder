package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.generic.FormElement
import kotlin.math.max

class Form(val context: Context,
           val viewGroup: ViewGroup,
           elements: MutableList<FormElement<*>>,
           val formStyleBundle: FormStyleBundle) {

    val elements: MutableList<FormElement<*>> = arrayListOf()

    init {
        for (element in elements) {
            this.elements.add(element)
        }
    }

    @Throws(ValidationException::class)
    fun validate() {
        for (formElement in elements) {
            formElement.validate()
        }
    }

    fun draw() {
        //draw everything first and then add height
        for (element in elements) {
            val view = element.createView(context, formStyleBundle)
            viewGroup.addView(view)
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