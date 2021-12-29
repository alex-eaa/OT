package yemelichevaleksandr.ot1.data

import java.util.*

data class Question(
    var question: String,
    val answersList: ArrayList<String>,
    val info: String,
)