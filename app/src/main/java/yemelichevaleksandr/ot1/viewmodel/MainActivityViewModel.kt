package yemelichevaleksandr.ot1.viewmodel

import androidx.lifecycle.ViewModel
import yemelichevaleksandr.ot1.updater.Updater
import javax.inject.Inject


class MainActivityViewModel : ViewModel() {

    @Inject
    lateinit var updater: Updater

    fun checkUpdate() {
        updater.checkUpdateTime()
    }
}