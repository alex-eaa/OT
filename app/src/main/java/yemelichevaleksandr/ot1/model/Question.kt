package yemelichevaleksandr.ot1.model

import java.util.*

data class Question(
    val question: String,
    val answersList: ArrayList<String>,
    val info: String,
)