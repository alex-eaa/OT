package yemelichevaleksandr.ot1.data

object QuestionRepositoryFactory {
    fun create(): QuestionRepository = QuestionRepositoryImpl()
}