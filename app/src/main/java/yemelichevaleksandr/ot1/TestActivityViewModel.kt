package yemelichevaleksandr.ot1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestActivityViewModel: ViewModel() {

    private val model = TestModel()

    private var numberCorrectAnswers = 0
    private var numberCurrentAnswers = 0
    private var questionArray: ArrayList<Question> = ArrayList(TestModel.NUMBER_QUESTIONS_IN_TEST)

    private val _question: MutableLiveData<Question> = MutableLiveData()
    val question: LiveData<Question> get() = _question

    private val _answerState: MutableLiveData<AnswerState> = MutableLiveData()
    val answerState: LiveData<AnswerState> get() = _answerState

    fun getFirstQuestion() {
        questionArray = model.getQuestions()
        renderQuestion(numberCurrentAnswers)
    }

    fun getNextQuestion() {
        numberCurrentAnswers++
        if (numberCurrentAnswers < TestModel.NUMBER_QUESTIONS_IN_TEST) {
            renderQuestion(numberCurrentAnswers)
        } else {
            _answerState.value = AnswerState.Result(numberCorrectAnswers)
        }
    }

    fun checkAnswer(answer: String) {
        if (questionArray[numberCurrentAnswers].answersList[0] == answer) {
            numberCorrectAnswers++
            _answerState.value = AnswerState.Yes
        } else {
            _answerState.value = AnswerState.No(questionArray[numberCurrentAnswers], answer)
        }
    }

    private fun renderQuestion(number: Int) {
        _question.value = questionArray[number]
    }

    fun onBackStop() {
        _answerState.value = AnswerState.Stop
    }

}