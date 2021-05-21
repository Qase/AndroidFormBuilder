package cz.qase.android.formbuilderlibrary

class FormStyleBundle(
    val primaryTextColor: Int = R.color.colorPrimary,
    val secondaryTextColor: Int = R.color.colorDefaultText,
    val primaryBackgroundColor: Int = R.color.colorBackgroundPrimary,
    val secondaryBackgroundColor: Int = R.color.colorBackground,
    val dividerColor: Int = R.color.colorGray,
    val disabledBackgroundColor: Int = R.color.colorGray,
    val neutralSymbolColor: Int = R.color.colorGray,
    val dangerousSymbolColor: Int = R.color.colorRed
) {


    companion object {

        fun colorBundleOne() = FormStyleBundle(
            R.color.oneTextPrimary,
            R.color.oneTextSecondary,
            R.color.oneBackgroundPrimary,
            R.color.oneBackgroundSecondary,
            R.color.oneDivider
        )

        fun colorBundleTwo() = FormStyleBundle(
            R.color.twoTextPrimary,
            R.color.twoTextSecondary,
            R.color.twoBackgroundPrimary,
            R.color.twoBackgroundSecondary,
            R.color.twoDivider
        )

        fun colorBundleThree() = FormStyleBundle(
            R.color.threeTextPrimary,
            R.color.threeTextSecondary,
            R.color.threeBackgroundPrimary,
            R.color.threeBackgroundSecondary,
            R.color.threeDivider,
            R.color.threeDivider
        )

        fun colorBundleFour() = FormStyleBundle(
            R.color.fourTextPrimary,
            R.color.fourTextSecondary,
            R.color.fourBackgroundPrimary,
            R.color.fourBackgroundSecondary,
            R.color.fourDivider
        )
    }
}



