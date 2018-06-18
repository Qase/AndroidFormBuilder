package cz.qase.android.formbuilderlibrary.element

import cz.qase.android.formbuilderlibrary.ValidationException
import cz.qase.android.formbuilderlibrary.validator.FormValidator

abstract class FormElementValidatable<T>(val formValidators: MutableList<FormValidator<T>> = ArrayList(),
                                         var invalid: Boolean = false,
                                         var invalidMessage: String? = null) : FormElement<T>() {

    fun addValidator(formValidator: FormValidator<T>) {
        formValidators.add(formValidator)
    }

    @Throws(ValidationException::class)
    override fun validate() {
        for (formValidator in formValidators) {
            try {
                formValidator.validate(getVal())
            } catch (e: ValidationException) {
                invalid = true
                invalidMessage = e.message
            }
        }
    }
}
