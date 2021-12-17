package yemelichevaleksandr.ot1.model.update

import yemelichevaleksandr.ot1.model.Question

class UpdateRepositoryImpl: UpdateRepository {
    override fun getVersionInRepository(): Int {
        TODO("Not yet implemented")
    }

    override fun downloadData(version: Int): String {
        TODO("Not yet implemented")
    }

    override fun parsingData(data: String): ArrayList<Question> {
        TODO("Not yet implemented")
    }
}