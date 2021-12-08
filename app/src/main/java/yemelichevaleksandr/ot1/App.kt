package yemelichevaleksandr.ot1

import android.app.Application
import android.content.Context

class App: Application() {

    companion object{

        lateinit var sApplication: Application

        fun getContext(): Context{
            return sApplication.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}