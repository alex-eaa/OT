package yemelichevaleksandr.ot1.model

import android.content.Context
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.empty
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError
import yemelichevaleksandr.ot1.App
import java.nio.charset.StandardCharsets

class UpdateTests {

    private val storage = FirebaseStorage.getInstance()
    private val rootRef = storage.reference

    private val sPref = App.getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
    private val version = App.getContext().getSharedPreferences("VERSION", 0)

    init {
        getFile()
            .map {
                var versionFile = 0
                for (item in it.items) {
                    if (item.name.contains("git")) {
                        val getVersionFile = item.name.drop(4).dropLast(4).toInt()
                        if (getVersionFile > versionFile) versionFile = getVersionFile
                    }
                }
                return@map versionFile
            }
            .map { fileVersion ->
                val currentVersion = sPref.getInt("VERSION", 0)
                if (fileVersion <= currentVersion) {
                    throw Exception("Нет новых данных")
                } else {
                    return@map fileVersion
                }
            }
            .flatMap { fileVersion ->
                Single.create<String> { emitter ->
                    val myRef = rootRef.child("git_$fileVersion.txt")
                    myRef.getBytes(1024 * 1024)
                        .addOnSuccessListener {
                            val receivedFile = String(it, StandardCharsets.UTF_8)
                            sPref.edit().putInt("VERSION", fileVersion).apply()
                            emitter.onSuccess(receivedFile)
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
                }
            }
            .map {
                Log.d("qqq", it)
            }
            .subscribe()
    }

    private fun getFile(): Single<ListResult> = Single.create { emitter ->
        rootRef.listAll()
            .addOnSuccessListener {
                emitter.onSuccess(it)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

}