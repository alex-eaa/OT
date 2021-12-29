package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.data.room.SettingEntity
import yemelichevaleksandr.ot1.data.room.entityToQuestion
import yemelichevaleksandr.ot1.data.room.listQuestionToEntity

class QuestionRepositoryImpl : QuestionRepository {

    override fun getRndQuestions(number: Int): Observable<Question> {
        return getQuestionDao().getQuestions(number)
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .map {
                entityToQuestion(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveAllQuestions(list: List<Question>) : Boolean{
        return getQuestionDao().updateAll(listQuestionToEntity(list))
    }
}