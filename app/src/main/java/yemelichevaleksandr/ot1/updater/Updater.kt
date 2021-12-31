package yemelichevaleksandr.ot1.updater

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.data.QuestionRepository
import yemelichevaleksandr.ot1.data.SettingsRepo
import yemelichevaleksandr.ot1.data.room.SettingEntity
import yemelichevaleksandr.ot1.updater.UpdateRepositoryImpl.Companion.PATTERN_VERSION_FILE
import javax.inject.Inject

class Updater @Inject constructor(
    private var questionRepo: QuestionRepository,
    private var settingsRepo: SettingsRepo,
    private var updateRepo: UpdateRepository,
) {

    fun checkUpdateTime() {
        settingsRepo.getSetting()
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (System.currentTimeMillis() - it.timeStamp > UPDATE_INTERVAL)
                    update(it.fileName)
                else
                    Log.d(TAG,
                        "До следующего обновления осталось: ${(UPDATE_INTERVAL - (System.currentTimeMillis() - it.timeStamp)) / 60000} минут")
            }, {
                Log.d(TAG, "ERROR database setting: ${it.message.toString()}")
                update(null)
            })
    }

    private fun update(fileNameInRoom: String?) {
        var newFileName = ""
        updateRepo.getFilenameWithLatestQuestions()
            .observeOn(Schedulers.io())
            .map { fileNameInFb ->
                Log.d(TAG, "Новейший файл вопросов в облаке: $fileNameInFb")
                Log.d(TAG, "Файл вопросов в базе: $fileNameInRoom")

                when {
                    fileNameInRoom == null -> fileNameInFb
                    isFileInFbNewer(fileNameInFb, fileNameInRoom) -> fileNameInFb
                    else -> {
                        settingsRepo.saveSetting(SettingEntity(
                            fileName = fileNameInRoom,
                            timeStamp = System.currentTimeMillis())
                        )
                        error("Версия билетов в базе актуальна ")
                    }
                }
            }
            .flatMap {
                newFileName = it
                updateRepo.getNewQuestions(it)
            }
            .observeOn(Schedulers.io())
            .map {
                val dbRes = questionRepo.saveAllQuestions(it)
                if (dbRes) {
                    settingsRepo.saveSetting(SettingEntity(
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

    private fun isFileInFbNewer(fileNameInFb: String, fileNameInRoom: String): Boolean {
        var verFileNameInFb = 0
        Regex(PATTERN_VERSION_FILE).find(fileNameInFb)?.let {
            verFileNameInFb = it.groupValues[1].toInt()
        }

        var verFileNameInRoom = 0
        Regex(PATTERN_VERSION_FILE).find(fileNameInRoom)?.let {
            verFileNameInRoom = it.groupValues[1].toInt()
        }

        return verFileNameInFb > verFileNameInRoom
    }

    companion object {
//        const val UPDATE_INTERVAL = 86400000
        const val UPDATE_INTERVAL = 111
        const val TAG = "qqq"
    }
}