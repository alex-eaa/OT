package yemelichevaleksandr.ot1.model

import java.util.*

data class Question(
    var question: String,
    val answersList: ArrayList<String>,
    val info: String,
)