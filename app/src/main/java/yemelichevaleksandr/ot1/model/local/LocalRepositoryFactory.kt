package yemelichevaleksandr.ot1.model.local

object LocalRepositoryFactory {
    fun create(): LocalRepository = StringArrayRepositoryImpl()
}