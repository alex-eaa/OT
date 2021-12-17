package yemelichevaleksandr.ot1.model.update

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Single
import yemelichevaleksandr.ot1.model.Question
import java.nio.charset.StandardCharsets

class UpdateRepositoryImpl : UpdateRepository {

    private val storage = FirebaseStorage.getInstance()
    private val rootRef = storage.reference

    override fun getLatestVersionNumber(): Single<Int> = getListAllFiles()
        .map {
            var version = 0
            it.forEach { file ->
                if (file.name.contains("git")) {
                    val getVersionFile = file.name.drop(4).dropLast(4).toInt()
                    if (getVersionFile > version) version = getVersionFile
                }
            }
            return@map version
        }

    override fun downloadData(version: Int): Single<String> = Single.create { emitter ->
        val myRef = rootRef.child("git_$version.txt")
        myRef.getBytes(1024 * 1024)
            .addOnSuccessListener {
                val receivedFile = String(it, StandardCharsets.UTF_8)
                emitter.onSuccess(receivedFile)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun parsingData(data: String): Single<ArrayList<Question>> {
        TODO("Not yet implemented")
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
}