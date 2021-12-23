package yemelichevaleksandr.ot1.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.model.local.room.listQuestionToEntity
import yemelichevaleksandr.ot1.model.update.UpdateRepository
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryFactory

class MainActivityViewModel : ViewModel() {

    private val update: UpdateRepository by lazy {
        UpdateRepositoryFactory.create()
    }

    private val sPref: SharedPreferences? =
        App.getContext()?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun update() {
        var newVersion = 0
        update.getLatestVersionNumber()
            .map { versionInFB ->
                Log.d(TAG, "Актуальная версия вопросов в облаке: $versionInFB")
                if (isNewVersion(versionInFB)) {
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
                if (dbRes) newVersion.let { version ->
                    sPref?.edit()?.putInt(PREF_VALUE_VERSION, version)?.apply()
                }
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Количество обновленных вопросов = ${it.size}")
            }, {
                Log.d(TAG, "ERROR: ${it.message.toString()}")
            })
    }

    private fun isNewVersion(versionInFB: Int): Boolean {
        sPref?.getInt(PREF_VALUE_VERSION, 0)?.let { versionInRoom ->
            return versionInFB > versionInRoom
        }
        return false
    }

    companion object{
        const val PREF_NAME = "SETTINGS"
        const val PREF_VALUE_VERSION = "VERSION"
        const val TAG = "qqq"
    }
}