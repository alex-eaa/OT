package yemelichevaleksandr.ot1.model.update

import android.util.Log
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

    override fun getLatestVersionNumber(): Single<String> = getListAllFiles()
        .map {
            var version = 0
            var fileNameMaxVersion = ""
            it.forEach { file ->
                Regex(PATTERN_VERSION_FILE).find(file.name)?.let { fileName ->
                    if (fileName.groupValues[1].toInt() > version) {
                        version = fileName.groupValues[1].toInt()
                        fileNameMaxVersion = fileName.groupValues[0]
                    }
                }
            }
            return@map fileNameMaxVersion
        }


    override fun downloadNewQuestions(fileName: String): Single<List<Question>> {
        return downloadFile(fileName)
            .flatMap {
                parsingData(it)
            }
    }

    override fun isFileInFbNewer(fileNameInFb: String, fileNameInRoom: String): Boolean {
        var verFileNameInFb = 0
        Regex(PATTERN_VERSION_FILE).find(fileNameInFb)?.let {
            verFileNameInFb = it.groupValues[1].toInt()
        }

        var verFileNameInRoom = 0
        Regex(PATTERN_VERSION_FILE).find(fileNameInRoom)?.let {
            verFileNameInRoom = it.groupValues[1].toInt()
        }

        return verFileNameInFb > verFileNameInRoom
    }


    private fun downloadFile(fileName: String): Single<String> = Single.create { emitter ->
        val myRef = rootRef.child(fileName)
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
        const val PATTERN_VERSION_FILE = "questions_v_(\\d+).xml"
    }
}