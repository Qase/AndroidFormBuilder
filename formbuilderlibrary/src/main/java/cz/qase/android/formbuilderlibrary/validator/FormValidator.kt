package cz.qase.android.formbuilderlibrary.validator

/**
 * Basic interface for form input validating
 */
interface FormValidator<in T> {
    /**
     * Throw ValidationException when error otherwise do nothing
     */
     fun validate(value: T?)
}
