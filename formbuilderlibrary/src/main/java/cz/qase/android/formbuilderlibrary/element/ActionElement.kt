package cz.qase.android.formbuilderlibrary.element

import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.R
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback


class ActionElement(
    actionCallback: ActionCallback,
    label: String,
    groupComponent: Int = R.layout.form_group_item_inline,
    headerComponent: Int = R.layout.form_inline_label,
    symbolComponent: Int = R.layout.form_button_symbol,
    groupComponentEnd: Int = R.layout.form_group_item_inline_end,
    formStyleBundle: FormStyleBundle? = null
) : NavigationElement(
    actionCallback,
    label,
    groupComponent,
    headerComponent,
    symbolComponent,
    groupComponentEnd,
    formStyleBundle
)