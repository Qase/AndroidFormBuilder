package cz.qase.android.formbuilderlibrary.validator

interface FormValidator<in T> {
     fun validate(value: T?)
}
