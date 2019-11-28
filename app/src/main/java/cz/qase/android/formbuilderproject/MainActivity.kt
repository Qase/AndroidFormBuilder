package cz.qase.android.formbuilderproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cz.qase.android.formbuilderlibrary.Form
import cz.qase.android.formbuilderlibrary.FormBuilder
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.element.ActionElement
import cz.qase.android.formbuilderlibrary.element.ActionTextElement
import cz.qase.android.formbuilderlibrary.element.EditTextElement
import cz.qase.android.formbuilderlibrary.element.HeaderElement
import cz.qase.android.formbuilderlibrary.element.LabelCheckboxElement
import cz.qase.android.formbuilderlibrary.element.LabelDateTimeElement
import cz.qase.android.formbuilderlibrary.element.LabelInputElement
import cz.qase.android.formbuilderlibrary.element.LabelSpinnerElement
import cz.qase.android.formbuilderlibrary.element.LabelSwitchElement
import cz.qase.android.formbuilderlibrary.element.LabelTextElement
import cz.qase.android.formbuilderlibrary.element.NavigationElement
import cz.qase.android.formbuilderlibrary.element.OpenableHeaderTextElement
import cz.qase.android.formbuilderlibrary.element.TextAreaElement
import cz.qase.android.formbuilderlibrary.element.TextElement
import cz.qase.android.formbuilderlibrary.element.generic.ActionCallback
import cz.qase.android.formbuilderlibrary.element.generic.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.generic.ValueCallback
import cz.qase.android.formbuilderlibrary.validator.EmailValidator
import cz.qase.android.formbuilderlibrary.validator.FormValidator
import cz.qase.android.formbuilderlibrary.validator.MaxLengthFormValidator
import cz.qase.android.formbuilderlibrary.validator.NotBlankFormValidator
import cz.qase.android.formbuilderlibrary.validator.NotEqualToValidator
import cz.qase.android.formbuilderlibrary.validator.NotInPastValidator
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var form: Form

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
    private val stringValues = arrayOf("one", "two", "three", "four", "five").map { "Option $it" }.toList()
    private val longerStringValues = (1..5).map { randomString(25) }

    private val notEmptyValidator = NotBlankFormValidator("This field is empty")
    private val emailValidator = EmailValidator("This is not valid email!")
    private val maxLengthValidator = MaxLengthFormValidator("This filed has more than 10 characters.", 10)

    private val validateActionCallback = object : ActionCallback, Form.Callback {
        override fun successCallback() {
            Toast.makeText(this@MainActivity, "Validate OK", Toast.LENGTH_SHORT).show()
        }

        override fun errorCallback(message: String?) {
            Toast.makeText(this@MainActivity, "Validate not ok $message", Toast.LENGTH_SHORT).show()
        }

        override fun callback() {
            form.sendForm(this)
        }
    }

    private val showToastActionCallback = object : ActionCallback {
        override fun callback() {
            Toast.makeText(this@MainActivity, "Action performed", Toast.LENGTH_SHORT).show()
        }
    }

    private val showToastCheckboxCallback = object : CheckboxCallback {
        private var toast: Toast? = null
        override fun callback(checked: Boolean) {
            toast?.cancel()
            toast = Toast.makeText(this@MainActivity, "Selected: $checked", Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    private val showToastStringValueCallback = object : ValueCallback<String> {
        private var toast: Toast? = null
        override fun callback(value: String) {
            toast?.cancel()
            toast = Toast.makeText(this@MainActivity, value, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }
    private val showToastDateTimeValueCallback = object : ValueCallback<DateTime> {
        private var toast: Toast? = null
        override fun callback(value: DateTime) {
            toast?.cancel()
            toast = Toast.makeText(this@MainActivity, SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault()).format(value.toDate()), Toast.LENGTH_SHORT)
            toast?.show()
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
        addElement(LabelSpinnerElement("LabelSpinnerElement label", "Option one", arrayListOf("Select...").plus(stringValues), null, formValidators = mutableListOf<FormValidator<String>>(NotEqualToValidator("Select...", "You have to select a value!"))), true)
        addElement(LabelCheckboxElement("LabelCheckboxElement label", true, showToastCheckboxCallback), true)
        addElement(LabelDateTimeElement("Date picker", "Vyberte datum...", supportFragmentManager, showToastDateTimeValueCallback, formValidators = arrayListOf(NotInPastValidator("Date cannot be in past"))))
        addSpace()


        //input validable elements
        addElement(HeaderElement("Input validable elements"), true)
        addElement(EditTextElement("Hint", "Edit text element text", object : ValueCallback<String> {
            override fun callback(value: String) {
                Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show()
            }
        }, arrayListOf(maxLengthValidator, notEmptyValidator)), true)
        addElement(LabelInputElement("Email", "test@test.cz", "", object : ValueCallback<String> {
            override fun callback(value: String) {
                Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show()
            }
        }, arrayListOf(emailValidator)), true)
        addElement(TextAreaElement("Fill textarea", null, showToastStringValueCallback, maxLength = 320))
        addSpace()

        //action elements
        addElement(HeaderElement("Action elements"), true)
        addElement(ActionElement(showToastActionCallback, "ActionElement label"), true)
        addElement(ActionTextElement(showToastActionCallback, "Action", "Click me"), true)
        addElement(NavigationElement(showToastActionCallback, "NavigationElement label"), true)
        addElement(ActionElement(validateActionCallback, "Validate all elements"))
        addSpace()

        return@with buildForm(this@MainActivity, formWrapper, FormStyleBundle.colorBundleThree())
    }


    private fun randomString(length: Int) = (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
}
