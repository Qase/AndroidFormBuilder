package cz.qase.android.formbuilderlibrary

import cz.qase.android.formbuilderlibrary.element.generic.FormElement

data class FormElementWrapper (
    val elements: List<FormElement<*>>,
    val groupId:String?
)