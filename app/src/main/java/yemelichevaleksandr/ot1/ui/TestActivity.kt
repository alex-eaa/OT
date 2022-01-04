package yemelichevaleksandr.ot1.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.Const.NUMBER_QUESTIONS_IN_TEST
import yemelichevaleksandr.ot1.R
import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.databinding.SecondactivityBinding
import yemelichevaleksandr.ot1.viewmodel.AnswerState
import yemelichevaleksandr.ot1.viewmodel.TestActivityViewModel


class TestActivity : AppCompatActivity() {

    private lateinit var binding: SecondactivityBinding

    private val viewModel: TestActivityViewModel by lazy {
        ViewModelProvider(this).get(TestActivityViewModel::class.java).apply {
            App.instance.component.inject(this)
        }
    }

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SecondactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardAnswer1.setOnClickListener { viewModel.checkAnswer(binding.answer1.text.toString()) }
        binding.cardAnswer2.setOnClickListener { viewModel.checkAnswer(binding.answer2.text.toString()) }
        binding.cardAnswer3.setOnClickListener { viewModel.checkAnswer(binding.answer3.text.toString()) }
        binding.cardAnswer4.setOnClickListener { viewModel.checkAnswer(binding.answer4.text.toString()) }
        binding.cardAnswer5.setOnClickListener { viewModel.checkAnswer(binding.answer5.text.toString()) }

        viewModel.question.observe(this, { question ->
            renderQuestion(question)
        })
        viewModel.getFirstQuestion()

        viewModel.answerState.observe(this, { answerState ->
            when (answerState) {
                is AnswerState.Yes -> showDialogYes()
                is AnswerState.No -> showDialogNo(answerState.question, answerState.answer)
                is AnswerState.Result -> showResult(answerState.numberCorrectAnswers)
                is AnswerState.Stop -> showDialogStop()
                else -> {}
            }
        })
    }

    private fun renderQuestion(question: Question) {
        val arrayBtn = arrayListOf(
            binding.answer1,
            binding.answer2,
            binding.answer3,
            binding.answer4,
            binding.answer5,
        )
        arrayBtn.shuffle()

        binding.tvQuestion.text = question.question
        arrayBtn[0].text = question.answersList[0]
        arrayBtn[1].text = question.answersList[1]
        arrayBtn[2].text = question.answersList[2]
        arrayBtn[3].text = question.answersList[3]
        arrayBtn[4].text = question.answersList[4]
    }

    private fun showDialogYes() {
        alertDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(this.getString(R.string.result_true))
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton(this.getString(R.string.button_next)) { _, _ ->
                viewModel.getNextQuestion()
            }
            .show()
    }

    private fun showDialogNo(question: Question, answer: String) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_no, null)
        dialogView.findViewById<TextView>(R.id.tv_you_answer).text = answer
        dialogView.findViewById<TextView>(R.id.tv_right_answer).text = question.answersList[0]
        dialogView.findViewById<TextView>(R.id.tv_item_rules).text = question.info

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle(this.getString(R.string.result_false))
            .setIcon(R.drawable.ic_baseline_cancel_24)
            .setPositiveButton(this.getString(R.string.button_next)) { _, _ ->
                viewModel.getNextQuestion()
            }
            .create()
        alertDialog?.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showResult(numberCorrectAnswers: Int) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_result, null)

        dialogView.findViewById<TextView>(R.id.tv_result).text =
            "$numberCorrectAnswers из $NUMBER_QUESTIONS_IN_TEST"

        val tvMark = dialogView.findViewById<TextView>(R.id.tv_rating)
        when {
            numberCorrectAnswers < 14 -> {
                tvMark.text = this.getString(R.string.result_unsatisfactory)
                tvMark.setTextColor(Color.RED)
            }
            numberCorrectAnswers in 14..15 -> {
                tvMark.text = this.getString(R.string.result_satisfactorily)
                tvMark.setTextColor(Color.MAGENTA)
            }
            numberCorrectAnswers in 16..19 -> {
                tvMark.text = this.getString(R.string.result_good)
                tvMark.setTextColor(Color.BLUE)
            }
            numberCorrectAnswers == 20 -> {
                tvMark.text = this.getString(R.string.result_perfectly)
                tvMark.setTextColor(Color.GREEN)
            }
        }

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle(this.getString(R.string.result))
            .setIcon(R.drawable.ic_baseline_mail_outline_24)
            .setPositiveButton(this.getString(R.string.button_home)) { _, _ -> finish() }
            .create()
        alertDialog?.show()
    }

    private fun showDialogStop() {
        val dialogView = this.layoutInflater.inflate(R.layout.dialog_stop, null)

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle(this.getString(R.string.stop))
            .setIcon(R.drawable.ic_baseline_close_24)
            .setPositiveButton(this.getString(R.string.button_yes)) { _, _ -> finish() }
            .setNegativeButton(this.getString(R.string.button_no)) { _, _ -> }
            .create()
        alertDialog?.show()
    }

    override fun onBackPressed() {
        viewModel.onBackStop()
    }
}