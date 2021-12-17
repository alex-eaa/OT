package yemelichevaleksandr.ot1.model.update

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question

interface UpdateRepository {
    fun getLatestVersionNumber(): Single<Int>
    fun downloadData(version: Int): Single<String>
    fun parsingData(data: String): Single<List<Question>>
}