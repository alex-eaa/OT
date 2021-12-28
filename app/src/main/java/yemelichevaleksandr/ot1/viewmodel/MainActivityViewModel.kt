package yemelichevaleksandr.ot1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.App.Companion.settings
import yemelichevaleksandr.ot1.model.local.LocalRepositoryFactory
import yemelichevaleksandr.ot1.model.local.room.SettingEntity
import yemelichevaleksandr.ot1.model.local.room.listQuestionToEntity
import yemelichevaleksandr.ot1.model.update.UpdateRepository
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryFactory

class MainActivityViewModel : ViewModel() {

    private val model = LocalRepositoryFactory.create()

    private val update: UpdateRepository by lazy {
        UpdateRepositoryFactory.create()
    }

    fun update() {
        var newFileName = ""
        update.getLatestVersionNumber()
            .observeOn(Schedulers.io())
            .map { fileNameInFb ->
                val fileNameInRoom = model.getSetting().fileName
                Log.d(TAG, "Новейший файл вопросов в облаке: $fileNameInFb")
                Log.d(TAG, "Файл вопросов в базе: $fileNameInRoom")
                if (update.isFileInFbNewer(fileNameInFb, fileNameInRoom)) {
                    fileNameInFb
                } else error("Версия билетов актуальна")
            }
            .flatMap {
                newFileName = it
                update.downloadNewQuestions(it)
            }
            .observeOn(Schedulers.io())
            .map {
                val dbRes = model.saveAllQuestions(it)
                if (dbRes) {
                    model.insertSetting(SettingEntity(
                        fileName = newFileName,
                        timeStamp = System.currentTimeMillis().toString())
                    )

                    Log.d(TAG, "Количество обновленных вопросов = ${it.size}")
                }
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Log.d(TAG, "ERROR: ${it.message.toString()}")
            })
    }

    companion object {
        const val TAG = "qqq"
    }
}