package yemelichevaleksandr.ot1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import yemelichevaleksandr.ot1.TestModel.Companion.NUMBER_QUESTIONS_IN_TEST
import yemelichevaleksandr.ot1.databinding.SecondactivityBinding

class TestActivity : MvpAppCompatActivity(), TestActivityView {

    private lateinit var binding: SecondactivityBinding
    private var alertDialog: AlertDialog? = null

    private val presenter by moxyPresenter { TestPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("qqq", "onCreate")

        binding = SecondactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bt1.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt2.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt3.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt4.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt5.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }

    }

    override fun renderQuestion(question: Question) {
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

    override fun showDialogYes() {
        alertDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("ВЕРНО !")
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton("Дальше") { _, _ -> presenter.getNextQuestion() }
            .show()
    }

    override fun showDialogNo(question: Question, answer: String) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_no, null)
        dialogView.findViewById<TextView>(R.id.tvNo2).text = answer
        dialogView.findViewById<TextView>(R.id.tvYes2).text = question.answersList[0]
        dialogView.findViewById<TextView>(R.id.tvPunkt2).text = question.info

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle("НЕ верно !")
            .setIcon(R.drawable.ic_baseline_cancel_24)
            .setPositiveButton("Дальше") { _, _ -> presenter.getNextQuestion() }
            .create()
        alertDialog?.show()
    }

    @SuppressLint("SetTextI18n")
    override fun showResult(numberCorrectAnswers: Int) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_result, null)

        dialogView.findViewById<TextView>(R.id.tvRight).text =
            "$numberCorrectAnswers из $NUMBER_QUESTIONS_IN_TEST"

        val tvMark = dialogView.findViewById<TextView>(R.id.tvMark)
        when {
            numberCorrectAnswers < 14 -> {
                tvMark.text = "неудовлетворительно"
                tvMark.setTextColor(Color.RED)
            }
            numberCorrectAnswers in 14..15 -> {
                tvMark.text = "удовлетворительно"
                tvMark.setTextColor(Color.MAGENTA)
            }
            numberCorrectAnswers in 16..19 -> {
                tvMark.text = "хорошо"
                tvMark.setTextColor(Color.BLUE)
            }
            numberCorrectAnswers == 20 -> {
                tvMark.text = "отлично"
                tvMark.setTextColor(Color.GREEN)
            }
        }

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle("Результат")
            .setIcon(R.drawable.ic_baseline_mail_outline_24)
            .setPositiveButton("На главную") { _, _ -> finish() }
            .create()
        alertDialog?.show()
    }

    override fun showDialogStop() {
        val dialogView = this.layoutInflater.inflate(R.layout.dialog_stop, null)

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setTitle("СТОП !")
            .setIcon(R.drawable.ic_baseline_close_24)
            .setPositiveButton("ДА") { _, _ -> finish() }
            .setNegativeButton("НЕТ") { _, _ -> presenter.dismissAlertDialog() }
            .create()
        alertDialog?.show()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun hideDialog() {
        alertDialog?.dismiss()
    }

    override fun onPause() {
        super.onPause()
        Log.d("qqq", "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("qqq", "onDestroy ")
    }

}