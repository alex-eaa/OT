package yemelichevaleksandr.ot1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.App.Companion.settings
import yemelichevaleksandr.ot1.model.local.room.listQuestionToEntity
import yemelichevaleksandr.ot1.model.update.UpdateRepository
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryFactory

class MainActivityViewModel : ViewModel() {

    private val update: UpdateRepository by lazy {
        UpdateRepositoryFactory.create()
    }

    fun update() {
        var newVersion = 0
        update.getLatestVersionNumber()
            .map { versionInFB ->
                Log.d(TAG, "Актуальная версия вопросов в облаке: $versionInFB")
                if (versionInFB > settings.version) {
                    newVersion = versionInFB
                    versionInFB
                } else error("Версия билетов актуальна")
            }
            .flatMap {
                update.downloadNewQuestions(newVersion)
            }
            .observeOn(Schedulers.io())
            .map {
                val dbRes = getQuestionDao().updateAll(listQuestionToEntity(it))
                if (dbRes) settings.version = newVersion
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Количество обновленных вопросов = ${it.size}")
                settings.countQuestions = it.size
            }, {
                Log.d(TAG, "ERROR: ${it.message.toString()}")
            })
    }


    companion object {
        const val TAG = "qqq"
    }
}