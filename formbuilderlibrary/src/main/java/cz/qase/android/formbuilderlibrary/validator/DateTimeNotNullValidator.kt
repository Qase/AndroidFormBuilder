package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException
import java.util.Date

class DateTimeNotNullValidator(private val errorMsg: String) : FormValidator<Date> {
    override fun validate(value: Date?) {
        if (value == null) {
            throw ValidationException(errorMsg)
        }
    }
}
