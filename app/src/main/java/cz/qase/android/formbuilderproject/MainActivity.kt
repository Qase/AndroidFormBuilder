package cz.qase.android.formbuilderproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cz.qase.android.formbuilderlibrary.FormBuilder
import cz.qase.android.formbuilderlibrary.FormStyleBundle
import cz.qase.android.formbuilderlibrary.element.HeaderTextViewElement
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val formBuilder = FormBuilder()
        val formStyleBundle = FormStyleBundle(R.color.colorGreen, R.color.colorOrange)
        formBuilder.addElement(HeaderTextViewElement("key1","Title", "Textung textung tydlidung"))
        formBuilder.addElement(HeaderTextViewElement("key2","Title2", "Textung textung tydlidung"))
        formBuilder.addElement(HeaderTextViewElement("key3","Title3", "Textung textung tydlidung"))
        formBuilder.addElement(HeaderTextViewElement("key4","Title4", "Textung textung tydlidung"))
        val form = formBuilder.buildForm(this, formWrapper, formStyleBundle)
        form.draw()


        val formBuilder2 = FormBuilder()
        val formStyleBundle2 = FormStyleBundle()
        formBuilder2.addElement(HeaderTextViewElement("key1","Title", "Textung textung tydlidung"))
        formBuilder2.addElement(HeaderTextViewElement("key2","Title2", "Textung textung tydlidung"))
        formBuilder2.addElement(HeaderTextViewElement("key3","Title3", "Textung textung tydlidung"))
        formBuilder2.addElement(HeaderTextViewElement("key4","Title4", "Textung textung tydlidung"))
        val form2 = formBuilder2.buildForm(this, formWrapper, formStyleBundle2)
        form2.draw()
    }
}
