package yemelichevaleksandr.ot1.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Observable
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.R
import java.util.*
import kotlin.collections.ArrayList


class TestModel {

    companion object {
        const val NUMBER_QUESTIONS_IN_TEST = 20
        const val NUMBER_QUESTIONS_ITEMS = 7
    }

    fun getQuestions(): List<Question> {
        var qqq : List<Question>? = null
        Observable.fromIterable(getArrayStrings())
            .buffer(NUMBER_QUESTIONS_ITEMS)
            .map {
                Question(
                    question = it[0],
                    info = it[1],
                    answersList = arrayListOf(it[2], it[3], it[4], it[5], it[6])
                )
            }
            .toList()
            .flatMapObservable { Observable.fromIterable(it.shuffled()) }
            .take(NUMBER_QUESTIONS_IN_TEST.toLong())
            .toList()
            .subscribe(
                { v -> qqq = v },
                { e -> println("Error: $e") }
            )

        return qqq!!
    }


    private fun getArrayStrings(): ArrayList<String> =
        App.getContext().resources.getStringArray(R.array.test).toList() as ArrayList<String>

}