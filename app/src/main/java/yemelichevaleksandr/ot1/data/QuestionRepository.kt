package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.data.room.SettingEntity

interface QuestionRepository {
    fun getRndQuestions(number: Int): Observable<Question>
    fun saveAllQuestions(list: List<Question>) : Boolean


}