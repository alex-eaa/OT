package yemelichevaleksandr.ot1.updater

object UpdateRepositoryFactory {
    fun create(): UpdateRepository = UpdateRepositoryImpl()
}