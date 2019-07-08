package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup
import cz.qase.android.formbuilderlibrary.element.generic.FormElement

class Form(val context: Context,
           val viewGroup: ViewGroup,
           private val elementsMap: LinkedHashMap<String, List<FormElement<*>>>,
           val formStyleBundle: FormStyleBundle) {

    @Throws(ValidationException::class)
    fun validate() {
        var validationException: ValidationException? = null
        for (mutableEntry in elementsMap) {
            for (formElement in mutableEntry.value) {
                try {
                    formElement.validate()
                } catch (e: ValidationException) {
                    validationException = e
                }
            }
        }

        if (validationException != null) {
            throw validationException
        }
    }

    fun draw() {
        //draw everything first and then add height
        for (mutableEntry in elementsMap) {
            for (element in mutableEntry.value) {
                val view = element.createView(context, formStyleBundle)
                viewGroup.addView(view)
            }
        }
    }

    fun reDraw() {
        viewGroup.removeAllViews()
        draw()
    }

    fun hide(id: String) {
        val list = elementsMap[id]
        if (list != null) {
            for (formElement in list) {
                formElement.hide()
            }
        }
    }

    fun show(id: String) {
        val list = elementsMap[id]
        if (list != null) {
            for (formElement in list) {
                formElement.show()
            }
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