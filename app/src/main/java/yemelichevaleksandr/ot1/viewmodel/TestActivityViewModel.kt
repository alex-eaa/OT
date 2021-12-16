package yemelichevaleksandr.ot1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.subjects.PublishSubject
import yemelichevaleksandr.ot1.model.Question
import yemelichevaleksandr.ot1.model.TestModel

class TestActivityViewModel : ViewModel() {

    private val model = TestModel()

    private var numberCorrectAnswers = 0
    private var numberCurrentQuestion = 1
    private lateinit var currentQuestion: Question

    private val questions: Observable<Question> = model.getQuestions()
    private val numberQuestionSubject: PublishSubject<Int> = PublishSubject.create()

    private val questionGenerate =
        Observable.zip(questions, numberQuestionSubject, BiFunction { question, number ->
            question.question = "$number. ${question.question}"
            currentQuestion = question
            return@BiFunction question
        })

    private val _question: MutableLiveData<Question> = MutableLiveData()
    val question: LiveData<Question> get() = _question

    private val _answerState: MutableLiveData<AnswerState> = MutableLiveData()
    val answerState: LiveData<AnswerState> get() = _answerState

    init {
        questionGenerate.subscribe({ _question.value = it }, {})
        numberQuestionSubject.onNext(numberCurrentQuestion)
    }

    fun getNextQuestion() {
        if (numberCurrentQuestion < TestModel.NUMBER_QUESTIONS_IN_TEST) {
            numberCurrentQuestion++
            numberQuestionSubject.onNext(numberCurrentQuestion)
        } else {
            _answerState.value = AnswerState.Result(numberCorrectAnswers)
        }
    }

    fun checkAnswer(answer: String) {
        if (currentQuestion.answersList[0] == answer) {
            numberCorrectAnswers++
            _answerState.value = AnswerState.Yes
        } else {
            _answerState.value = AnswerState.No(currentQuestion, answer)
        }
    }

    fun onBackStop() {
        _answerState.value = AnswerState.Stop
    }

}