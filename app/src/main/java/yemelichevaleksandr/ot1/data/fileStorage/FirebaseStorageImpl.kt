package yemelichevaleksandr.ot1.data.fileStorage

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream
import java.util.zip.ZipEntry
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

    override fun downloadFile(fileName: String): Single<String> = Single.create { emitter ->
        val myRef = rootRef.child(fileName)
        myRef.getBytes(1024 * 1024)
            .addOnSuccessListener {

                val stream = it.inputStream()

                val buffer = ByteArray(1024 * 1024)

                var receivedFile: String = ""
                var count: Int
                ZipInputStream(stream).use { zis ->
                    var ze: ZipEntry?
                    while (zis.nextEntry.also {zipEntry->
                            ze = zipEntry } != null) {

                        val fileNameInZip = ze?.name

                        while (zis.read(buffer).also { count = it } != -1)
                            Log.d("qqq", "fileName = $fileNameInZip")
                        receivedFile = String(buffer, StandardCharsets.UTF_8)
                        //Log.d("qqq", "receivedFile = $receivedFile")
                    }
                }
//                val receivedFile = byttearray?.let { it1 -> String(it1, StandardCharsets.UTF_8) }
                emitter.onSuccess(receivedFile)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }
}