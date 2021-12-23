package yemelichevaleksandr.ot1.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.databinding.ActivityMainBinding
import yemelichevaleksandr.ot1.model.local.room.QuestionEntity
import yemelichevaleksandr.ot1.model.local.room.listQuestionToEntity
import yemelichevaleksandr.ot1.model.local.room.questionToEntity
import yemelichevaleksandr.ot1.model.update.UpdateRepository
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val update: UpdateRepository by lazy {
        UpdateRepositoryFactory.create()
    }

    private val sPref: SharedPreferences? =
        App.getContext()?.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        startUpdate()
    }

    private fun startUpdate() {
        var versionSavedInDB: Int? = null
        update.getLatestVersionNumber()
            .map { versionInFB ->
                Log.d("qqq", "getLatestVersionNumber = $versionInFB")
                sPref?.getInt("VERSION", 0)?.let { versionInRoom ->
                    if (versionInFB > versionInRoom) {
                        versionSavedInDB = versionInFB
                    } else {
                        throw Exception("Нет новых данных")
                    }
                }
                return@map versionSavedInDB!!
            }
            .flatMap { version ->
                update.downloadNewQuestions(version)
            }
            .observeOn(Schedulers.io())
            .map{
                getQuestionDao().updateAll(listQuestionToEntity(it))
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("qqq", "Количество загруженных вопросов = ${it.size}")
                versionSavedInDB?.let { version ->
                    sPref?.edit()?.putInt("VERSION", version)?.apply()
                }
            }, {
                Log.d("qqq", "ERROR: ${it.message.toString()}")
            })
    }
}