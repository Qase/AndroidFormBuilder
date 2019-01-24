package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException

/**
 * Field validator
 *
 * Is ok when value is not null and not blank (aka empty or made only of whitespaces)
 * see String.isNullOrBlank()
 */
class NotBlankFormValidator(private val errorMsg: String) : FormValidator<String> {
    override fun validate(value: String?) {
        if (value.isNullOrBlank()) {
            throw ValidationException(errorMsg)
        }
    }
}
