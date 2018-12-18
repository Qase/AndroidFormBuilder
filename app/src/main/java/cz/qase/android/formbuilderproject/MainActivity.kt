package cz.qase.android.formbuilderproject

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cz.qase.android.formbuilderlibrary.FormBuilder
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.element.CheckboxCallback
import cz.qase.android.formbuilderlibrary.element.EditTextElement
import cz.qase.android.formbuilderlibrary.element.HeadCheckboxElement
import cz.qase.android.formbuilderlibrary.element.HeaderElement
import cz.qase.android.formbuilderlibrary.element.HeaderTextElement
import cz.qase.android.formbuilderlibrary.validator.FormValidator
import cz.qase.android.formbuilderlibrary.validator.NotBlankFormValidator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val formBuilder = FormBuilder()
        val formStyleBundle = FormStyleBundle(R.color.colorGreen, R.color.colorOrange)
        formBuilder.addElement(HeaderTextElement("key1", "Nadpis a text", "Text nadpisu a textu"))
        formBuilder.addElement(HeaderElement("key2", "Sekce imputů"))

        val notBlankFormValidator = NotBlankFormValidator("HEJ!!!")
        formBuilder.addElement(EditTextElement("key3", "Input text", "Smaž mě a bude chyba", mutableListOf<FormValidator<String>>(notBlankFormValidator)))
        formBuilder.addElement(EditTextElement("key4", "Input text2", "Smaž mě a bude chyba", mutableListOf<FormValidator<String>>(notBlankFormValidator)))
        formBuilder.addElement(EditTextElement("key5", "Input text3", "Smaž mě a bude chyba", mutableListOf<FormValidator<String>>(notBlankFormValidator)))
        formBuilder.addElement(HeadCheckboxElement("key5", true, "Title", "", object : CheckboxCallback {
            override fun callback(checked: Boolean) {
                Log.e("HeadCheckboxElement", checked.toString())
            }
        }))
        val form = formBuilder.buildForm(this, formWrapper)
        form.draw()


    }
}
