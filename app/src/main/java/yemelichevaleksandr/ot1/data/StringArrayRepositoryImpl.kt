package yemelichevaleksandr.ot1.data

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import yemelichevaleksandr.ot1.R
import javax.inject.Inject


class StringArrayRepositoryImpl : QuestionRepository {

    @Inject
    lateinit var context: Context

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

    override fun saveAllQuestions(list: List<Question>): Boolean {
        return false
    }

    private fun getArrayStrings(): ArrayList<String> =
        context.resources?.getStringArray(R.array.test)?.toList() as ArrayList<String>
}