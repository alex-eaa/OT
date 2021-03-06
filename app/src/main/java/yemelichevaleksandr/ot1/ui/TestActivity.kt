package yemelichevaleksandr.ot1.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.R
import yemelichevaleksandr.ot1.data.Question
import yemelichevaleksandr.ot1.databinding.SecondactivityBinding
import yemelichevaleksandr.ot1.viewmodel.AnswerState
import yemelichevaleksandr.ot1.viewmodel.TestActivityViewModel
import yemelichevaleksandr.ot1.viewmodel.TestActivityViewModel.Companion.NUMBER_QUESTIONS_IN_TEST


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

        binding.bt1.setOnClickListener { if (it is Button) viewModel.checkAnswer(it.text.toString()) }
        binding.bt2.setOnClickListener { if (it is Button) viewModel.checkAnswer(it.text.toString()) }
        binding.bt3.setOnClickListener { if (it is Button) viewModel.checkAnswer(it.text.toString()) }
        binding.bt4.setOnClickListener { if (it is Button) viewModel.checkAnswer(it.text.toString()) }
        binding.bt5.setOnClickListener { if (it is Button) viewModel.checkAnswer(it.text.toString()) }

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
            }
        })
    }

    private fun renderQuestion(question: Question) {
        val arrayBtn = arrayListOf(
            binding.bt1,
            binding.bt2,
            binding.bt3,
            binding.bt4,
            binding.bt5,
        )
        arrayBtn.shuffle()

        binding.tvVopros.text = question.question
        arrayBtn[0].text = question.answersList[0]
        arrayBtn[1].text = question.answersList[1]
        arrayBtn[2].text = question.answersList[2]
        arrayBtn[3].text = question.answersList[3]
        arrayBtn[4].text = question.answersList[4]
    }

    private fun showDialogYes() {
        alertDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("?????????? !")
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton("????????????") { _, _ ->
                viewModel.getNextQuestion()
            }
            .show()
    }

    private fun showDialogNo(question: Question, answer: String) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_no, null)
        dialogView.findViewById<TextView>(R.id.tvNo2).text = answer
        dialogView.findViewById<TextView>(R.id.tvYes2).text = question.answersList[0]
        dialogView.findViewById<TextView>(R.id.tvPunkt2).text = question.info

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle("???? ?????????? !")
            .setIcon(R.drawable.ic_baseline_cancel_24)
            .setPositiveButton("????????????") { _, _ ->
                viewModel.getNextQuestion()
            }
            .create()
        alertDialog?.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showResult(numberCorrectAnswers: Int) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_result, null)

        dialogView.findViewById<TextView>(R.id.tvRight).text =
            "$numberCorrectAnswers ???? $NUMBER_QUESTIONS_IN_TEST"

        val tvMark = dialogView.findViewById<TextView>(R.id.tvMark)
        when {
            numberCorrectAnswers < 14 -> {
                tvMark.text = "??????????????????????????????????????"
                tvMark.setTextColor(Color.RED)
            }
            numberCorrectAnswers in 14..15 -> {
                tvMark.text = "??????????????????????????????????"
                tvMark.setTextColor(Color.MAGENTA)
            }
            numberCorrectAnswers in 16..19 -> {
                tvMark.text = "????????????"
                tvMark.setTextColor(Color.BLUE)
            }
            numberCorrectAnswers == 20 -> {
                tvMark.text = "??????????????"
                tvMark.setTextColor(Color.GREEN)
            }
        }

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle("??????????????????")
            .setIcon(R.drawable.ic_baseline_mail_outline_24)
            .setPositiveButton("???? ??????????????") { _, _ -> finish() }
            .create()
        alertDialog?.show()
    }

    private fun showDialogStop() {
        val dialogView = this.layoutInflater.inflate(R.layout.dialog_stop, null)

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle("???????? !")
            .setIcon(R.drawable.ic_baseline_close_24)
            .setPositiveButton("????") { _, _ -> finish() }
            .setNegativeButton("??????") { _, _ -> }
            .create()
        alertDialog?.show()
    }

    override fun onBackPressed() {
        viewModel.onBackStop()
    }
}