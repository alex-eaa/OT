package yemelichevaleksandr.ot1.model.local

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question
import yemelichevaleksandr.ot1.model.local.room.SettingEntity

interface LocalRepository {
    fun getRndQuestions(number: Int): Observable<Question>
    fun saveAllQuestions(list: List<Question>) : Boolean

    fun getSetting(): Maybe<SettingEntity>
    fun insertSetting(entity: SettingEntity)
}