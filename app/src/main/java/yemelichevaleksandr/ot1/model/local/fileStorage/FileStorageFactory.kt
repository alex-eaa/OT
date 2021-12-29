package yemelichevaleksandr.ot1.model.local.fileStorage

object FileStorageFactory {
    fun create(): FileStorage = FirebaseStorageImpl()
}