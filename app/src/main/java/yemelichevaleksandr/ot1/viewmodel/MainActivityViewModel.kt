package yemelichevaleksandr.ot1.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.App.Companion.settings
import yemelichevaleksandr.ot1.model.local.LocalRepositoryFactory
import yemelichevaleksandr.ot1.model.local.room.listQuestionToEntity
import yemelichevaleksandr.ot1.model.update.UpdateRepository
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryFactory

class MainActivityViewModel : ViewModel() {

    private val model = LocalRepositoryFactory.create()

    private val update: UpdateRepository by lazy {
        UpdateRepositoryFactory.create()
    }

    fun update() {
        var newVersion = 0
        update.getLatestVersionNumber()
            .map { fileNmaeInFb ->
                Log.d(TAG, "Новейший файл вопросов в облаке: $fileNmaeInFb")
                    //TODO ("Получить имя файла из room")
                if (compareVersionFilename(fileNmaeInFb, fileNmaeInRoom)) {
                    fileNmaeInFb
                } else error("Версия билетов актуальна")
            }
            .flatMap {
                update.downloadNewQuestions(it)
            }
            .observeOn(Schedulers.io())
            .map {
                val dbRes = model.saveAllQuestions(it)
                if (dbRes) {
                    settings.version = newVersion
                    settings.countQuestions = it.size
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

    fun compareVersionFilename(fileNameInFb: String, fileNameInRoom: String): Boolean {
        //TODO("Сравнить версии файлов")
        return true
    }


    companion object {
        const val TAG = "qqq"
    }
}