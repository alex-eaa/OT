package yemelichevaleksandr.ot1.model.local

import io.reactivex.rxjava3.core.Observable
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.R
import yemelichevaleksandr.ot1.model.Question
import kotlin.collections.ArrayList


class StringArrayRepositoryImpl : LocalRepository {

    companion object {
        const val NUMBER_QUESTIONS_ITEMS = 7
    }

    override fun getRndQuestions(number: Int): Observable<Question> = Observable.fromIterable(getArrayStrings())
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
        .take(number.toLong())
        .toList()
        .flatMapObservable { Observable.fromIterable(it) }

    override fun saveAllQuestions(list: ArrayList<Question>) {
        //TODO("Not yet implemented")
    }

    private fun getArrayStrings(): ArrayList<String> =
        App.getContext()?.resources?.getStringArray(R.array.test)?.toList() as ArrayList<String>
}