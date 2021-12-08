package yemelichevaleksandr.ot1

import android.content.Context
import moxy.MvpPresenter
import yemelichevaleksandr.ot1.TestModel.Companion.NUMBER_QUESTIONS_IN_TEST

class TestPresenter() : MvpPresenter<TestActivityView>() {
    private val model = TestModel()

    private var numberCorrectAnswers = 0
    private var numberCurrentAnswers = 0
    private var questionArray: ArrayList<Question> = ArrayList(NUMBER_QUESTIONS_IN_TEST)

    private fun getFirstQuestion() {
        questionArray = model.getQuestions()
        renderQuestion(numberCurrentAnswers)
    }

    fun getNextQuestion() {
        numberCurrentAnswers++
        if (numberCurrentAnswers < NUMBER_QUESTIONS_IN_TEST) {
            viewState.hideDialog()
            renderQuestion(numberCurrentAnswers)
        } else {
            viewState.showResult(numberCorrectAnswers)
        }
    }

    override fun attachView(view: TestActivityView?) {
        super.attachView(view)
        getFirstQuestion()
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

    fun dismissAlertDialog() {
        viewState.hideDialog()
    }

    fun onBackPressed(){
        viewState.showDialogStop()
    }

}