package yemelichevaleksandr.ot1.model.local

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.App.Companion.getQuestionDao
import yemelichevaleksandr.ot1.model.Question
import yemelichevaleksandr.ot1.model.local.room.SettingEntity
import yemelichevaleksandr.ot1.model.local.room.entityToQuestion
import yemelichevaleksandr.ot1.model.local.room.listQuestionToEntity

class RoomRepositoryImpl : LocalRepository {

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

    override fun getSetting(): SettingEntity {
        return getQuestionDao().getSetting()
    }

    override fun insertSetting(entity: SettingEntity) {
        getQuestionDao().insertSetting(entity)
    }
}