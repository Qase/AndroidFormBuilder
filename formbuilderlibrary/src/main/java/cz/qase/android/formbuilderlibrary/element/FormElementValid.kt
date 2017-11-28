package cz.qase.android.formbuilderlibrary.element

import cz.qase.android.formbuilderlibrary.validator.FormValidator

abstract class FormElementValid<T>(key: String) : FormElement<T>(key) {

    override fun validate(){

    }
}
