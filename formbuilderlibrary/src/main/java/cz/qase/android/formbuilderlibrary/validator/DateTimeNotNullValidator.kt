package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException
import org.joda.time.DateTime

class DateTimeNotNullValidator(private val errorMsg: String) : FormValidator<DateTime> {
    override fun validate(value: DateTime?) {
        if (value == null) {
            throw ValidationException(errorMsg)
        }
    }
}
