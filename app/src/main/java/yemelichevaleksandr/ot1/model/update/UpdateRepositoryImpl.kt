package yemelichevaleksandr.ot1.model.update

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question
import java.nio.charset.StandardCharsets
import java.util.*

class UpdateRepositoryImpl : UpdateRepository {

    private val storage = FirebaseStorage.getInstance()
    private val rootRef = storage.reference

    override fun getLatestVersionNumber(): Single<Int> = getListAllFiles()
        .map {
            var version = 0
            it.forEach { file ->
                if (file.name.contains("tst")) {
                    val getVersionFile = file.name.drop(4).dropLast(4).toInt()
                    if (getVersionFile > version) version = getVersionFile
                }
            }
            return@map version
        }


    override fun downloadNewQuestions(version: Int): Single<List<Question>> {
        return downloadFile(version)
            .flatMap {
                parsingData(it)
            }
    }


    private fun downloadFile(version: Int): Single<String> = Single.create { emitter ->
        val myRef = rootRef.child("tst_$version.xml")
        myRef.getBytes(1024 * 1024)
            .addOnSuccessListener {
                val receivedFile = String(it, StandardCharsets.UTF_8)
                emitter.onSuccess(receivedFile)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    private fun parsingData(data: String): Single<List<Question>> {
        return getNewTestLines(data)
            .skip(NUMBER_QUESTIONS_ITEMS.toLong())
            .buffer(NUMBER_QUESTIONS_ITEMS)
            .map {
                Question(
                    question = it[1],
                    info = it[2],
                    answersList = arrayListOf(it[3], it[4], it[5], it[6], it[7])
                )
            }
            .toList()
    }

    private fun getListAllFiles(): Single<List<StorageReference>> = Single.create { emitter ->
        rootRef.listAll()
            .addOnSuccessListener {
                emitter.onSuccess(it.items)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    private fun getNewTestLines(data: String): Observable<String> {
        return Observable.create { emitter ->
            Regex(PATTERN_QUESTION_BLOCK).findAll(data)
                .map { Regex(PATTERN_QUESTION_ITEMS).findAll(it.groupValues[0]) }
                .forEach {
                    it.forEach {
                        emitter.onNext(it.groupValues[2])
                    }
                }
            emitter.onComplete()
        }
    }

    companion object {
        const val NUMBER_QUESTIONS_ITEMS = 9
        const val PATTERN_QUESTION_BLOCK = "<Row[\\d\\D]+?</Row>"
        const val PATTERN_QUESTION_ITEMS =
            "<Cell ss:StyleID=\".+\"><Data ss:Type=\"(String|Number)\">(.+?)</Data>"
    }
}