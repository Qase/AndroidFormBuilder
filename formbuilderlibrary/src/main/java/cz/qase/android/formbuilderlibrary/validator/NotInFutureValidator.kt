package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException
import org.joda.time.DateTime

/**
 * MaxLengthFormValidator
 *
 * Check if length of form value is bigger than specified value
 */
class NotInFutureValidator(
        private val errorMsg: String
) : FormValidator<DateTime> {
    override fun validate(value: DateTime?) {
        if (value != null && value.isAfterNow) {
            throw ValidationException(errorMsg)
        }
    }
}
