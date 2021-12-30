package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.data.room.QuestionDao
import yemelichevaleksandr.ot1.data.room.entityToQuestion
import yemelichevaleksandr.ot1.data.room.listQuestionToEntity
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao,
) : QuestionRepository {

    override fun getRndQuestions(number: Int): Observable<Question> {
        return questionDao.getQuestions(number)
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .map {
                entityToQuestion(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveAllQuestions(list: List<Question>): Boolean {
        return questionDao.updateAll(listQuestionToEntity(list))
    }
}