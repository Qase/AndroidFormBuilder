package cz.qase.android.formbuilderproject

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import cz.qase.android.formbuilderlibrary.Form
import cz.qase.android.formbuilderlibrary.FormBuilder
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.element.*
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.MaxLengthFormValidator
import cz.qase.android.formbuilderlibrary.validator.NotBlankFormValidator
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : Activity() {

    private lateinit var form: Form

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z')
    private val stringValues = arrayOf("one", "two", "three", "four", "five").map { "Option $it" }.toList()
    private val longerStringValues = (1..5).map { randomString(25) }

    private val notEmptyValidator = NotBlankFormValidator("This field is empty")
    private val maxLengthValidator = MaxLengthFormValidator("This filed has more than 10 characters.", 10)

    private val validateActionCallback = object : ActionCallback, Form.Callback {
        override fun successCallback() {
            Toast.makeText(this@MainActivity, "Validate OK", Toast.LENGTH_LONG).show()
        }

        override fun errorCallback(message: String?) {
            Toast.makeText(this@MainActivity, "Validate not ok $message", Toast.LENGTH_LONG).show()
        }

        override fun callback() {
            form.sendForm(this)
        }
    }

    private val showToastActionCallback = object : ActionCallback {
        override fun callback() {
            Toast.makeText(this@MainActivity, "Action performed", Toast.LENGTH_LONG).show()
        }
    }

    private val showToastCheckboxCallback = object : CheckboxCallback {
        override fun callback(checked: Boolean) {
            Toast.makeText(this@MainActivity, "Selected: $checked", Toast.LENGTH_LONG).show()
        }
    }

    private val showToastStringValueCallback = object : ValueCallback<String> {
        override fun callback(value: String) {
            Toast.makeText(this@MainActivity, value, Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        form = buildForm()
        form.draw()
        //how to get inout data??
//        form.sendForm(object: Fomr.)

    }

    private fun buildForm() = with(FormBuilder()) {


        //App Header
        addElement(HeaderElement("AndroidFormBuilder test app"), true)

        //static elements
        addElement(HeaderElement("Static elements"), true)
        addElement(TextElement("TextElement value"), true)
        addElement(LabelTextElement("LabelTextElement label", "LabelTextElement text"), true)
        addElement(OpenableHeaderTextElement("OpenableHeaderTextElement label", longerStringValues))
        addSpace()

        //input elements
        addElement(HeaderElement("Input elements"), true)
        addElement(LabelSwitchElement("LabelSwitchElement label", true, showToastCheckboxCallback), true)
        addElement(LabelSpinnerElement("LabelSpinnerElement label", "Option one", stringValues, showToastStringValueCallback), true)
        addElement(LabelCheckboxElement("LabelCheckboxElement label", true, showToastCheckboxCallback))
        addSpace()


        //input validable elements
        addElement(HeaderElement("Input validable elements"), true)
        addElement(EditTextElement("Hint", "Edit text element text", arrayListOf(maxLengthValidator, notEmptyValidator)))
        addSpace()

        //action elements
        addElement(HeaderElement("Action elements"), true)
        addElement(ActionElement(showToastActionCallback, "ActionElement label", "ActionElement text"), true)
        addElement(NavigationElement(showToastActionCallback, "NavigationElement label"), true)
        addElement(ActionElement(validateActionCallback, "Validate all elements", ""))
        addSpace()

        return@with buildForm(this@MainActivity, formWrapper, FormStyleBundle.colorBundleOne())
    }


    private fun randomString(length: Int) = (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
}
