package yemelichevaleksandr.ot1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import yemelichevaleksandr.ot1.model.Question
import yemelichevaleksandr.ot1.model.TestModel

class TestActivityViewModel : ViewModel() {

    private val model = TestModel()

    private var numberCorrectAnswers = 0
    private var numberCurrentAnswers = 0
    private var questions: List<Question> = model.getQuestions()

    private val _question: MutableLiveData<Question> = MutableLiveData()
    val question: LiveData<Question> get() = _question

    private val _answerState: MutableLiveData<AnswerState> = MutableLiveData()
    val answerState: LiveData<AnswerState> get() = _answerState

    init {
        renderQuestion(numberCurrentAnswers)
    }

    fun getNextQuestion() {
        numberCurrentAnswers++
        if (numberCurrentAnswers < questions.size) {
            renderQuestion(numberCurrentAnswers)
        } else {
            _answerState.value = AnswerState.Result(numberCorrectAnswers)
        }
    }

    fun checkAnswer(answer: String) {
        if (questions[numberCurrentAnswers].answersList[0] == answer) {
            numberCorrectAnswers++
            _answerState.value = AnswerState.Yes
        } else {
            _answerState.value = AnswerState.No(questions[numberCurrentAnswers], answer)
        }
    }

    private fun renderQuestion(number: Int) {
        _question.value = questions[number]
    }

    fun onBackStop() {
        _answerState.value = AnswerState.Stop
    }

}