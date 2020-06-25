package cz.qase.android.formbuilderlibrary.validator

import android.util.Patterns
import cz.qase.android.formbuilderlibrary.ValidationException

/**
 * Field validator
 *
 * Is ok when value is not null and not blank (aka empty or made only of whitespaces)
 * see String.isNullOrBlank()
 */
class NullableEmailValidator(private val errorMsg: String) : FormValidator<String> {
    override fun validate(value: String?) {
        if (!value.isNullOrBlank()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                throw ValidationException(errorMsg)
            }
        }
    }
}
