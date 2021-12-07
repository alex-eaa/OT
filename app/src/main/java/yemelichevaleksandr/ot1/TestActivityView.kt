package yemelichevaleksandr.ot1

import moxy.MvpView
import moxy.viewstate.strategy.*


@StateStrategyType(SingleStateStrategy::class)
interface TestActivityView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun renderQuestion(question: Question)
    fun showDialogYes()
    fun showDialogNo(question: Question, answer: String)
    fun showResult(numberCorrectAnswers: Int)
}