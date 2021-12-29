package yemelichevaleksandr.ot1.data.fileStorage

import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Single

interface FileStorage {
    fun getListAllFiles() : Single<List<StorageReference>>
    fun downloadFile(fileName: String): Single<String>
}