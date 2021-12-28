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

    fun checkUpdateTime() {
        model.getSetting()
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (System.currentTimeMillis() - it.timeStamp > 86400000)
                    update(it.fileName)
                else
                    Log.d(TAG,
                        "До следующего обновления осталось: ${(86400000 - (System.currentTimeMillis() - it.timeStamp)) / 60000} минут")
            }, {
                Log.d(TAG, "ERROR database setting: ${it.message.toString()}")
                update(null)
            })
    }


    private fun update(fileNameInRoom: String?) {
        var newFileName = ""
        update.getLatestVersionNumber()
            .observeOn(Schedulers.io())
            .map { fileNameInFb ->
                Log.d(TAG, "Новейший файл вопросов в облаке: $fileNameInFb")
                Log.d(TAG, "Файл вопросов в базе: $fileNameInRoom")

                when {
                    fileNameInRoom == null -> fileNameInFb
                    update.isFileInFbNewer(fileNameInFb, fileNameInRoom) -> fileNameInFb
                    else -> {
                        model.insertSetting(SettingEntity(
                            fileName = fileNameInRoom,
                            timeStamp = System.currentTimeMillis())
                        )
                        error("Версия билетов в базе актуальна ")
                    }
                }
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
                        timeStamp = System.currentTimeMillis())
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