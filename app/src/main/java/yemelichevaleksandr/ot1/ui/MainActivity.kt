package yemelichevaleksandr.ot1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.databinding.ActivityMainBinding
import yemelichevaleksandr.ot1.model.update.UpdateRepository
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryFactory
import yemelichevaleksandr.ot1.model.update.UpdateRepositoryImpl
import yemelichevaleksandr.ot1.model.update.UpdateTests

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val update: UpdateRepository by lazy {
        UpdateRepositoryFactory.create()
    }

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
        update.getLatestVersionNumber()
            .flatMap {
                Log.d("qqq", "Start download")
                update.downloadData(it)
            }
            .flatMap {
                Log.d("qqq", "Start parsing")
                update.parsingData(it)
            }
            .flatMapObservable{
                Observable.fromIterable(it)
            }
            .subscribe({
                Log.d("qqq", "subscribe = ${it.question}")
            }, {
                Log.d("qqq", it.message.toString())
            })
    }
}