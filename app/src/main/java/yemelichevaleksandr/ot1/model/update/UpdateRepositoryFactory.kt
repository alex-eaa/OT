package yemelichevaleksandr.ot1.model.update

object UpdateRepositoryFactory {
    fun create(): UpdateRepository = UpdateRepositoryImpl()
}