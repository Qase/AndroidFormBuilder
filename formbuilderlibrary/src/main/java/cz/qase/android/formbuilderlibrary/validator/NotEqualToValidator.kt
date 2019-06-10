package cz.qase.android.formbuilderlibrary.validator

import cz.qase.android.formbuilderlibrary.ValidationException

class NotEqualToValidator<T>(private val refValue: T, private val errorMsg: String) : FormValidator<T> {
    override fun validate(value: T?) {
        if (refValue?.equals(value) == true) {
            throw ValidationException(errorMsg)
        }
    }
}
