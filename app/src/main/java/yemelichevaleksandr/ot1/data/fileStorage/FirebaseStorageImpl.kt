package yemelichevaleksandr.ot1.data.fileStorage

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Single
import java.nio.charset.StandardCharsets

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

    override fun downloadFile(fileName: String): Single<String> = Single.create { emitter ->
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

}