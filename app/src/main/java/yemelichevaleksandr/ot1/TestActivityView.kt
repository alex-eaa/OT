package yemelichevaleksandr.ot1

interface TestActivityView {
    fun renderQuestion(question: Question)
    fun showDialogYes()
    fun showDialogNo(question: Question, answer: String)
    fun showResult(numberCorrectAnswers: Int)
}