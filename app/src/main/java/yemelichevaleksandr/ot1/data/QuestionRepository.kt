package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.core.Observable

interface QuestionRepository {
    fun getRndQuestions(number: Int): Observable<Question>
    fun saveAllQuestions(list: List<Question>) : Boolean


}