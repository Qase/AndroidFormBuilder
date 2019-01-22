package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException

/**
 * OnlyNumValidator class
 *
 * Check if given field value contains only letters
 */
class OnlyNumValidator(
        private val errorMsg: String = "Field value contains other characters than just digits."
) : FormValidator<String> {

    override fun validate(value: String?) {

        if (value == null) {
            throw ValidationException("Field value is null")
        }


        val isNotNumOnly = value.any { it.isLetter() }

        if (isNotNumOnly) {
            throw ValidationException(errorMsg)
        }
    }
}

