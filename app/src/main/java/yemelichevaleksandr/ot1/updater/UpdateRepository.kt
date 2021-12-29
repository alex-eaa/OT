package yemelichevaleksandr.ot1.updater

import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.data.Question

interface UpdateRepository {
    fun getLatestVersionNumber(): Single<String>
    fun downloadNewQuestions(fileName: String): Single<List<Question>>
    fun isFileInFbNewer(fileNameInFb: String, fileNameInRoom: String): Boolean
}