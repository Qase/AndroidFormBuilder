package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException

/**
 * MaxLengthFormValidator
 *
 * Check if length of form value is bigger than specified value
 */
class MaxLengthFormValidator(
        private val errorMsg: String,
        private val max: Int
) : FormValidator<String> {
    override fun validate(value: String?) {
        if (value != null && value.length > max) {
            throw ValidationException(errorMsg)
        }
    }
}
