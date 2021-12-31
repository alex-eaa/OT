package yemelichevaleksandr.ot1.data.fileStorage

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Single
import java.util.zip.ZipInputStream


class FirebaseStorageImpl : FileStorage {

    private val storage = FirebaseStorage.getInstance()
    private val rootRef = storage.reference

    override fun getListAllFiles(): Single<List<StorageReference>> = Single.create { emitter ->
        rootRef.listAll()
            .addOnSuccessListener {
                emitter.onSuccess(it.items)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    // Для загрузки несжатого файла
//    override fun downloadFile(fileName: String): Single<String> = Single.create { emitter ->
//        val myRef = rootRef.child(fileName)
//        myRef.getBytes(1024 * 1024)
//            .addOnSuccessListener {
//                val receivedFile = String(it, StandardCharsets.UTF_8)
//                emitter.onSuccess(receivedFile)
//            }
//            .addOnFailureListener {
//                emitter.onError(it)
//            }
//    }

    // Для загрузки файла сжатого zip
    override fun downloadFile(fileName: String): Single<String> = Single.create { emitter ->
        val myRef = rootRef.child(fileName)
        myRef.getBytes(1024 * 1024).addOnSuccessListener {
            val stream = it.inputStream()
            ZipInputStream(stream).use { zip ->
                zip.nextEntry?.let { entry ->
                    if (!entry.isDirectory && entry.name.endsWith(".xml")) {
                        Log.d(TAG, "Файл внутри архива: ${entry.name} size = ${entry.size} byte")
                        emitter.onSuccess(String(zip.readBytes()))
                    }
                }
            }
            emitter.onError(Throwable("Файл в архиве не правильный"))
        }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    companion object {
        const val TAG = "FileStorage"
    }
}