package cz.qase.android.formbuilderproject

import android.app.Application
import com.facebook.stetho.Stetho
import net.danlew.android.joda.JodaTimeAndroid

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(applicationContext)
        Stetho.initializeWithDefaults(this)
    }
}