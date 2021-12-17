package yemelichevaleksandr.ot1.model.update

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question
import yemelichevaleksandr.ot1.model.local.StringArrayRepositoryImpl
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

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
            Log.d("qqq", "Latest Number = $version")
            return@map version
        }

    override fun downloadData(version: Int): Single<String> = Single.create { emitter ->
        val myRef = rootRef.child("tst_$version.xml")
        myRef.getBytes(1024 * 1024)
            .addOnSuccessListener {
                Log.d("qqq", "Загрузка завершена")
                val receivedFile = String(it, StandardCharsets.UTF_8)
                emitter.onSuccess(receivedFile)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun parsingData(data: String): Single<List<Question>> {
        return getNewTestLines(data)
            .skip(10)
            .buffer(NUMBER_QUESTIONS_ITEMS)
            .map {
                Log.d("qqq", "flatMapObservable map")
                Question(
                    question = it[0],
                    info = it[1],
                    answersList = arrayListOf(it[2], it[3], it[4], it[5], it[6])
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
            Regex(PATTERN_QUESTION_BLOCK).findAll(data).forEach { block ->
                Regex(PATTERN_QUESTION_ITEMS).findAll(block.groupValues[0]).forEach {
                    emitter.onNext(it.groupValues[1])
                }
            }
            emitter.onComplete()
        }
    }

    companion object {
        const val NUMBER_QUESTIONS_ITEMS = 7
        const val PATTERN_QUESTION_BLOCK = "<Row[\\d\\D]+?</Row>"
        const val PATTERN_QUESTION_ITEMS =
            "<Cell ss:StyleID=\".+\"><Data ss:Type=\"String\">([\\d\\D]+?)</Data>[\\d\\D]+?</Cell>"
    }
}