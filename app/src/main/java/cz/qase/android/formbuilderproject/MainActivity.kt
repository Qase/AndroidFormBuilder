package cz.qase.android.formbuilderproject

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import cz.qase.android.formbuilderlibrary.Form
import cz.qase.android.formbuilderlibrary.FormBuilder
import cz.qase.android.formbuilderlibrary.element.*
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.MaxLengthFormValidator
import cz.qase.android.formbuilderlibrary.validator.NotBlankFormValidator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private lateinit var form: Form

    private val stringValues = ('a'..'f').map { it.toString() }.toList()

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
        addElement(HeaderElement("AndroidFormBuilder test app"))
        addDivider()

        //static elements
        addElement(HeaderElement("Static elements"))
        addDivider()
        addElement(TextElement("TextElement value"))
        addDivider()
        addElement(LabelTextElement("LabelTextElement value", "LabelTextElement text"))
        addDivider()
        addElement(OpenableHeaderTextElement("OpenableHeaderTextElement label", "OpenableHeaderTextElement value"))
        addDivider()

        //input elements
        addElement(HeaderElement("Input elements"))
        addDivider()
        addElement(LabelSwitchElement("LabelSwitchElement title", true, showToastCheckboxCallback))
        addDivider()
        addElement(LabelSpinnerElement("LabelSpinnerElement label", "b", stringValues, showToastStringValueCallback))
        addDivider()


        //input validable elements
        addElement(HeaderElement("Input validable elements"))
        addDivider()
        addElement(EditTextElement("Hint", "Edit text element text", arrayListOf(maxLengthValidator, notEmptyValidator)))
        addDivider()

        //action elements
        addElement(HeaderElement("Action elements"))
        addDivider()
        addElement(ActionElement(showToastActionCallback, "ActionElement Label", "ActionElement text"))
        addDivider()
        addElement(NavigationElement(showToastActionCallback, "NavigationElement label"))
        addDivider()
        addElement(ActionElement(validateActionCallback, "Validate all elements", ""))
        addDivider()

        return@with buildForm(this@MainActivity, formWrapper)
    }
}
