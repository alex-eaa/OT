package yemelichevaleksandr.ot1

sealed class AnswerState{
    data class No(val question: Question, val answer: String) : AnswerState()
    data class Result(val numberCorrectAnswers: Int) : AnswerState()
    object Yes : AnswerState()
    object Stop : AnswerState()
}
