package cz.qase.android.formbuilderlibrary.element.generic

import android.content.Context
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.ValidationException

abstract class FormElement<T>() {

    protected var enabled = true

    fun enable(context: Context, formStyleBundle: FormStyleBundle) {
        enabled = true
        enableElement(context, formStyleBundle)
    }

    fun disable(context: Context, formStyleBundle: FormStyleBundle) {
        enabled = false
        disableElement(context, formStyleBundle)
    }

    abstract fun createView(context: Context, formStyleBundle: FormStyleBundle): View

    @Throws(ValidationException::class)
    abstract fun validate()

    abstract fun getVal(): T?
    protected abstract fun enableElement(context: Context, formStyleBundle: FormStyleBundle)
    protected abstract fun disableElement(context: Context, formStyleBundle: FormStyleBundle)
    abstract fun hide()
    abstract fun show()

}