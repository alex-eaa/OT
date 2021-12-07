package yemelichevaleksandr.ot1

import android.content.Context
import moxy.MvpPresenter
import yemelichevaleksandr.ot1.TestModel.Companion.NUMBER_QUESTIONS_IN_TEST

class TestPresenter(private val context: Context) : MvpPresenter<TestActivityView>() {
    private val model = TestModel()

    private var numberCorrectAnswers = 0
    private var numberCurrentAnswers = 0
    private var questionArray: ArrayList<Question> = ArrayList(NUMBER_QUESTIONS_IN_TEST)

    fun getFirstQuestion() {
        questionArray = model.getQuestions(context)
        renderQuestion(numberCurrentAnswers)
    }

    fun getNextQuestion() {
        numberCurrentAnswers++
        if (numberCurrentAnswers < NUMBER_QUESTIONS_IN_TEST) {
            renderQuestion(numberCurrentAnswers)
        } else {
            viewState.showResult(numberCorrectAnswers)
        }
    }


    private fun renderQuestion(number: Int) {
        viewState.renderQuestion(questionArray[number])
    }

    fun checkAnswer(answer: String) {
        if (questionArray[numberCurrentAnswers].answersList[0] == answer) {
            numberCorrectAnswers++
            viewState.showDialogYes()
        } else {
            viewState.showDialogNo(questionArray[numberCurrentAnswers], answer)
        }
    }

}