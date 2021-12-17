package yemelichevaleksandr.ot1.model.local

import io.reactivex.rxjava3.core.Observable
import yemelichevaleksandr.ot1.model.Question

interface LocalRepository {
    fun getRndQuestions(number: Int): Observable<Question>
    fun saveAllQuestions(list: ArrayList<Question>)
}