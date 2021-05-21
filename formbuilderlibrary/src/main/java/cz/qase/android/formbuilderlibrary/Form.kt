package cz.qase.android.formbuilderlibrary

import android.content.Context
import android.view.ViewGroup

class Form(val context: Context,
           val viewGroup: ViewGroup,
           private val elementsMap: LinkedHashMap<String, FormElementWrapper>,
           val formStyleBundle: FormStyleBundle) {

    @Throws(ValidationException::class)
    fun validate() {
        var validationException: ValidationException? = null
        for (mutableEntry in elementsMap) {
            for (formElement in mutableEntry.value.elements) {
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
            for (element in mutableEntry.value.elements) {
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
        val formElementWrapper = elementsMap[id]
        if (formElementWrapper != null) {
            for (formElement in formElementWrapper.elements) {
                formElement.hide()
            }
        }
    }

    fun show(id: String) {
        val formElementWrapper = elementsMap[id]
        if (formElementWrapper != null) {
            for (formElement in formElementWrapper.elements) {
                formElement.show()
            }
        }
    }

    fun disableGroup(groupId:String){
        for (mutableEntry in elementsMap) {
            val value = mutableEntry.value
            if(value.groupId==groupId){
                for (formElement in value.elements) {
                    formElement.disable(context, formStyleBundle)
                }
            }
        }
    }
    fun enableGroup(groupId:String){
        for (mutableEntry in elementsMap) {
            val value = mutableEntry.value
            if(value.groupId==groupId){
                for (formElement in value.elements) {
                    formElement.enable(context, formStyleBundle)
                }
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