package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException

class NotBlankFormValidator(val errorMsg: String) : FormValidator<String> {
    override fun validate(value: String?) {
        if (value.isNullOrBlank()) {
            throw ValidationException(errorMsg)
        }
    }
}
