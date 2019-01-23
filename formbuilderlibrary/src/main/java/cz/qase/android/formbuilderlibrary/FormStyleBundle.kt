package cz.qase.android.formbuilderlibrary

class FormStyleBundle(val primaryTextColor: Int = R.color.colorPrimary,
                      val secondaryTextColor: Int = R.color.colorDefaultText,
                      val primaryBackgroundColor: Int = R.color.colorBackgroundPrimary,
                      val secondaryBackgroundColor: Int = R.color.colorBackground,
                      val dividerColor: Int = R.color.colorGreen) {


    companion object {

        fun colorBundleOne() = FormStyleBundle(
                R.color.oneTextPrimary,
                R.color.oneTextSecondary,
                R.color.oneBackgroundPrimary,
                R.color.oneBackgroundSecondary,
                R.color.oneDivider
        )
    }
}



