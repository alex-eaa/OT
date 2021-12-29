package yemelichevaleksandr.ot1.data.fileStorage

object FileStorageFactory {
    fun create(): FileStorage = FirebaseStorageImpl()
}