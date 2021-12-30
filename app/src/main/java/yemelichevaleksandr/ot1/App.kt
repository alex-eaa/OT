package yemelichevaleksandr.ot1

import android.app.Application
import yemelichevaleksandr.ot1.di.ApplicationComponent
import yemelichevaleksandr.ot1.di.DaggerApplicationComponent


class App : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerApplicationComponent.builder().setContext(this).build()
    }

    companion object {
        lateinit var instance: App
    }
}