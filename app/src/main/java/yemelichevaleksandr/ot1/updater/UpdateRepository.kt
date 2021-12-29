package yemelichevaleksandr.ot1.updater

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.data.Question

interface UpdateRepository {
    fun getFilenameWithLatestQuestions(): Single<String>
    fun getNewQuestions(fileName: String): Single<List<Question>>
}