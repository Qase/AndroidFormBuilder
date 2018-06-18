package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.View
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.ValidationException

abstract class FormElement<T>() {

    abstract fun createView(context: Context, formStyleBundle: FormStyleBundle): View

    @Throws(ValidationException::class)
    abstract fun validate()

    abstract fun getVal(): T?

}