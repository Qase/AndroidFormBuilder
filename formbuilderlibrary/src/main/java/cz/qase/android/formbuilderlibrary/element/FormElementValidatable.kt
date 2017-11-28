package cz.qase.android.formbuilderlibrary.element

import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.validator.FormValidator

abstract class FormElementValidatable<T>(key: String, val formValidators: MutableList<FormValidator<T>> = ArrayList()) : FormElement<T>(key) {

    fun addValidator(formValidator: FormValidator<T>) {
        formValidators.add(formValidator)
    }

    @Throws(ValidationException::class)
    override fun validate() {
        for (formValidator in formValidators) {
            formValidator.validate(getVal())
        }
    }
}
