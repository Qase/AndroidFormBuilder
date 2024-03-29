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
import cz.qase.android.formbuilderlibrary.element.NavigationWithErrorElement
import cz.qase.android.formbuilderlibrary.element.OpenableHeaderTextElement
import cz.qase.android.formbuilderlibrary.element.RadioButtonsElement
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var form: Form

    private lateinit var toast: Toast


    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
    private val INPUTS_ID = "INPUTS_ID"
    private val stringValues = arrayOf(
        "one",
        "two",
        "three",
        "four",
        "really long long long long long five"
    ).map { "Option $it" }.toList()
    private val fewStringValues = arrayOf(
        "one",
        "two"
    ).map { "Option $it" }.toList()

    private val longerStringValues = (1..5).map { randomString(25) }

    private val notEmptyValidator = NotBlankFormValidator("This field is empty")
    private val emailValidator = EmailValidator("This is not valid email!")
    private val maxLengthValidator =
        MaxLengthFormValidator("This filed has more than 10 characters.", 10)

    private val validateActionCallback = object : ActionCallback, Form.Callback {
        override fun successCallback() {
            showMessage("Validate OK")
        }

        override fun errorCallback(message: String?) {
            showMessage("Validate not ok $message")
        }

        override fun callback() {
            form.sendForm(this)
        }
    }

    private fun showMessage(message: String) {
        toast.setText(message)
        toast.show()
    }

    private val showToastActionCallback = object : ActionCallback {
        override fun callback() {
            showMessage("Action performed")
        }
    }

    private val showToastCheckboxCallback = object : CheckboxCallback {
        override fun callback(checked: Boolean) {
            showMessage("Selected: $checked")
        }
    }

    private val showToastStringValueCallback = object : ValueCallback<String> {
        override fun callback(value: String) {
            showMessage(value)
        }
    }
    private val showToastDateTimeValueCallback = object : ValueCallback<Date> {
        override fun callback(value: Date) {
            showMessage(SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault()).format(value))

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast = Toast.makeText(this, "Toast", Toast.LENGTH_SHORT)
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
        addElement(
            LabelTextElement(
                "LabelTextElement label with very long text which needs to overflow",
                "LabelTextElement text with very long text which needs to overflow"
            ), true
        )
        addElement(
            LabelTextElement(
                "Label",
                "LabelTextElement text with very long text which needs to overflow"
            ), true
        )
        addElement(OpenableHeaderTextElement("OpenableHeaderTextElement label", longerStringValues))
        addSpace()

        //input elements
        addElement(HeaderElement("Input elements"), true)
        addElement(
            LabelSwitchElement("Enable inputs", true, object : CheckboxCallback {
                override fun callback(checked: Boolean) {
                    if (checked) {
                        form.enableGroup(INPUTS_ID)
                    } else {
                        form.disableGroup(INPUTS_ID)
                    }
                }
            }, "ON", "OFF"),
            true
        )
        addElement(
            LabelSwitchElement("LabelSwitchElement label", true, showToastCheckboxCallback),
            true,
            groupId = INPUTS_ID
        )
        addElement(
            LabelSwitchElement(
                "LabelSwitchNoteElement label",
                true,
                showToastCheckboxCallback,
                noteCallback = showToastActionCallback
            ),
            true,
            groupId = INPUTS_ID
        )
        addElement(
            LabelSpinnerElement(
                "LabelSpinnerElement label",
                "Option one",
                arrayListOf("Select...").plus(stringValues),
                null,
                formValidators = mutableListOf<FormValidator<String>>(
                    NotEqualToValidator(
                        "Select...",
                        "You have to select a value!"
                    )
                )
            ), true,
            groupId = INPUTS_ID
        )
        addElement(
            LabelCheckboxElement(
                "LabelCheckboxElement label",
                true,
                showToastCheckboxCallback
            ), true,
            groupId = INPUTS_ID
        )
        addElement(
            LabelDateTimeElement(
                "Date picker",
                "Vyberte datum...",
                supportFragmentManager,
                showToastDateTimeValueCallback,
                formValidators = arrayListOf(NotInPastValidator("Date cannot be in past"))
            ),
            false,
            groupId = INPUTS_ID
        )
        addElement(
            EditTextElement(
                "Hint",
                "Edit text element text",
                valueChangeListener = object : ValueCallback<String> {
                    override fun callback(value: String) {
                        showMessage(value)
                    }
                },
                password = false,
                formValidators = arrayListOf(maxLengthValidator, notEmptyValidator)
            ), true,
            groupId = INPUTS_ID
        )

        addElement(
            RadioButtonsElement(
                "Option one",
                fewStringValues,
                valueCallback = object : ValueCallback<String> {
                    override fun callback(value: String) {
                        showMessage(value)
                    }
                }
            ), true,
            groupId = INPUTS_ID
        )

        addSpace()


        //input validable elements
        addElement(HeaderElement("Input validable elements"), true)
        addElement(
            EditTextElement(
                "Hint",
                "Edit text element text",
                valueChangeListener = object : ValueCallback<String> {
                    override fun callback(value: String) {
                        showMessage(value)
                    }
                },
                password = false,
                formValidators = arrayListOf(maxLengthValidator, notEmptyValidator)
            ), true
        )
        addElement(
            EditTextElement(
                "Password", "password",
                valueChangeListener = object : ValueCallback<String> {
                    override fun callback(value: String) {
                        showMessage(value)
                    }
                }, password = true, formValidators = arrayListOf(notEmptyValidator)
            ), true
        )

        addElement(LabelInputElement("Email", "test@test.cz", "", object : ValueCallback<String> {
            override fun callback(value: String) {
                showMessage(value)
            }
        }, arrayListOf(emailValidator)), true)
        addElement(
            TextAreaElement(
                "MMMMMMMMMMMMMMMMMMMM",
                null,
                showToastStringValueCallback,
                maxLength = 320
            )
        )
        addElement(
            TextAreaElement(
                "Fill textarea with extra long hint, becouse it sucks to be bald, whatever man, do what you want",
                null,
                showToastStringValueCallback,
                maxLength = 320
            )
        )
        addSpace()

        //action elements
        addElement(HeaderElement("Action elements"), true)
        addElement(ActionElement(showToastActionCallback, "ActionElement label"), true)
        addElement(ActionTextElement(showToastActionCallback, "Action", "Click me"), true)
        addElement(NavigationElement(showToastActionCallback, "NavigationElement label"), true)
        addElement(
            NavigationWithErrorElement(
                showToastActionCallback,
                "NavigationWithErrorElement label"
            ), true
        )
        addElement(ActionElement(validateActionCallback, "Validate all elements"))
        addSpace()

        return@with buildForm(this@MainActivity, formWrapper, FormStyleBundle.colorBundleThree())
    }


    private fun randomString(length: Int) = (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}
