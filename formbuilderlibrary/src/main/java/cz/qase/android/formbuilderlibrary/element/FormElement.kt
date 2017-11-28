package cz.qase.android.formbuilderlibrary.element

import android.content.Context
import android.view.View
import cz.qase.android.formbuilderlibrary.FormBuilder
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.ValidationException

abstract class FormElement<T>(val key: String) {

    abstract fun createView(context: Context, formStyleBundle: FormStyleBundle): View

    abstract fun validate()

    abstract fun getVal(): T?

}