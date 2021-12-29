package yemelichevaleksandr.ot1.viewmodel

import androidx.lifecycle.ViewModel
import yemelichevaleksandr.ot1.updater.Updater


class MainActivityViewModel : ViewModel() {

    private val updater = Updater()

    fun checkUpdate() {
        updater.checkUpdateTime()
    }
}