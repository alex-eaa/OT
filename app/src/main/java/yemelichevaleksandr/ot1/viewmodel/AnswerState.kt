package yemelichevaleksandr.ot1.viewmodel

import yemelichevaleksandr.ot1.data.Question

sealed class AnswerState{
    data class No(val question: Question, val answer: String) : AnswerState()
    data class Result(val numberCorrectAnswers: Int) : AnswerState()
    object Yes : AnswerState()
    object Stop : AnswerState()
}
