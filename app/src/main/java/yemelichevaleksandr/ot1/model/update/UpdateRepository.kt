package yemelichevaleksandr.ot1.model.update

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question

interface UpdateRepository {
    fun getLatestVersionNumber(): Single<String>
    fun downloadNewQuestions(fileName: String): Single<List<Question>>
    fun isFileInFbNewer(fileNameInFb: String, fileNameInRoom: String): Boolean
}