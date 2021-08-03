package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException
import java.util.Date

/**
 * MaxLengthFormValidator
 *
 * Check if length of form value is bigger than specified value
 */
class NotInPastValidator(
    private val errorMsg: String
) : FormValidator<Date> {
    override fun validate(value: Date?) {
        if (value != null && value.before(Date())) {
            throw ValidationException(errorMsg)
        }
    }
}
