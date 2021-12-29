package yemelichevaleksandr.ot1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.subjects.PublishSubject
import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.data.QuestionRepositoryFactory

class TestActivityViewModel : ViewModel() {
    private val model = QuestionRepositoryFactory.create()

    private var numberCorrectAnswers = 0
    private var numberCurrentQuestion = 1
    private lateinit var currentQuestion: Question

    private val _question: MutableLiveData<Question> = MutableLiveData()
    val question: LiveData<Question> get() = _question

    private val _answerState: MutableLiveData<AnswerState> = MutableLiveData()
    val answerState: LiveData<AnswerState> get() = _answerState

    private val questions: Observable<Question> = model.getRndQuestions(NUMBER_QUESTIONS_IN_TEST)
    private val numberQuestionSubject: PublishSubject<Int> = PublishSubject.create()

    private val questionGenerate =
        Observable.zip(questions, numberQuestionSubject, BiFunction { question, number ->
            question.question = "$number. ${question.question}"
            currentQuestion = question
            return@BiFunction question
        })
            .subscribe(
                {
                    _question.value = it
                }, {
                    Log.d("qqq", "error ${it.message}")
                }
            )

    init {
        numberQuestionSubject.onNext(numberCurrentQuestion)
    }

    fun getNextQuestion() {
        if (numberCurrentQuestion < NUMBER_QUESTIONS_IN_TEST) {
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

    companion object {
        const val NUMBER_QUESTIONS_IN_TEST = 20
    }
}