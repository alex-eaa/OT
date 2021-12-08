package yemelichevaleksandr.ot1

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface TestActivityView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun renderQuestion(question: Question)
    fun showDialogYes()
    fun showDialogNo(question: Question, answer: String)
    fun showResult(numberCorrectAnswers: Int)
    fun showDialogStop()
    fun hideDialog()
}