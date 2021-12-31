package yemelichevaleksandr.ot1.data

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import yemelichevaleksandr.ot1.data.mappers.MapperForQuestion
import yemelichevaleksandr.ot1.data.room.QuestionDao
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao,
    private val mapper : MapperForQuestion
) : QuestionRepository {

    override fun getRndQuestions(number: Int): Observable<Question> {
        return questionDao.getQuestions(number)
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .map {
                mapper.map(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveAllQuestions(list: List<Question>): Boolean {
        return questionDao.updateAll(mapper.map(list))
    }
}