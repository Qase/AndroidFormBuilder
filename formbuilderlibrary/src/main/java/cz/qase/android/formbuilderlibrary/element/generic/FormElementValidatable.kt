package cz.qase.android.formbuilderlibrary.element.generic

import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.validator.FormValidator

abstract class FormElementValidatable<T>(val formValidators: MutableList<FormValidator<T>> = ArrayList()) : FormElement<T>() {

    fun addValidator(formValidator: FormValidator<T>) {
        formValidators.add(formValidator)
    }

    @Throws(ValidationException::class)
    override fun validate() {
        if (enabled) {
            for (formValidator in formValidators) {
                formValidator.validate(getVal())
            }
        }
    }
}
