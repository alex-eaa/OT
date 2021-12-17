package yemelichevaleksandr.ot1.model.update

import yemelichevaleksandr.ot1.model.Question

interface UpdateRepository {
    fun getVersionInRepository(): Int
    fun downloadData(version: Int): String
    fun parsingData(data: String): ArrayList<Question>
}