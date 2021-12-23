package yemelichevaleksandr.ot1.model.update

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question

interface UpdateRepository {
    fun getLatestVersionNumber(): Single<Int>
    fun downloadNewQuestions(version: Int): Single<List<Question>>
}