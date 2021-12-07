package yemelichevaleksandr.ot1

import android.content.Context
import yemelichevaleksandr.ot1.TestModel.Companion.NUMBER_QUESTIONS_IN_TEST

class TestPresenter(private val view: TestActivityView, val context: Context) {
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
        }else {
            view.showResult(numberCorrectAnswers)
        }
    }


    private fun renderQuestion(number: Int) {
        view.renderQuestion(questionArray[number])
    }

    fun checkAnswer(answer: String) {
        if (questionArray[numberCurrentAnswers].answersList[0] == answer){
            numberCorrectAnswers++
            view.showDialogYes()
        }else{
            view.showDialogNo(questionArray[numberCurrentAnswers], answer)
        }
    }

}